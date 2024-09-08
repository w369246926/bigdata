package com.flink.netty.test;

import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * <P>
 * PL起動クラス。設定ファイル情報の取得と、設定に従ったTCPポート毎のレジからの待ち受けを行う
 * </P>
 * 
 * 作者：niwa
 */
public class SNetHttpProxyServer {
    /** ChannelGroup的名字 */
    private static final String CHANNEL_GROUP_NAME = "SNetHttpProxyServer";
    /** 设置TCP后台日志记录时的关键字 */
    private static final String TCP_BACKLOG_KEY = "backlog";
    /** 设置TCP接收缓冲区大小时的关键字 */
    private static final String TCP_RECEIVE_BUFFER_SIZE_KEY = "receiveBufferSize";
    /** 设置TCP发送缓冲区大小时的关键字 */
    private static final String TCP_SEND_BUFFER_SIZE_KEY = "child.sendBufferSize";
    /** 每个父执行器的最大线程数 */
    private static final int BOSS_EXECUTOR_MAX_THREAD_NUM = 1;
    /** SNQT记录器 */
    private static SnqtLoggerEx snqtlog = LoggerFactoryEx.getSnqtLogger();
    /** 每个代表业务号的ServerBootstrap列表 */
    private List<ServerBootstrap> bootstrapList = new ArrayList<ServerBootstrap>();

    /**
     * 创建自身并开始启动过程
     * 
     * @param args 启动参数
     */
    public static void main(String[] args) {
        (new SNetHttpProxyServer()).run(args);
    }

    /**
     * 执行启动过程.
     * <p>
     * 1. 获取PL配置文件路径并设置Log4j。<br>
     * 2. 获取和检查各个配置文件的设置值。<br>
     * 3. 进行EJB连接的初始设置。<br>
     * 4. 如果在上述1-3步骤中发生异常，则中止启动过程。<br>
     * 5. 将IP地址添加到LoggerFactory以排除日志输出。<br>
     * 6. 为每个代表业务号生成ServerBootstrap。<br>
     * 6-1. 创建ServerBootstrap。<br>
     * 6-2. 创建管道。<br>
     * 6-3. 如果指定了TCP发送缓冲区大小，则进行设置。<br>
     * 6-4. 绑定ServerBootstrap并添加到ChannelGroup中。<br>
     * 6-5. 如果指定了TCP后台日志记录大小，则进行设置。<br>
     * 6-6. 如果指定了TCP接收缓冲区大小，则进行设置。<br>
     * 7. 启动ShutdownHookListener并等待停止请求。<br>
     * 8. 如果收到停止请求，则执行停止处理。（在实际运行中，使用pkill停止，因此不会执行）<br>
     * </p>
     * 
     * @param args 启动参数
     */
    public void run(String[] args) {
        try {
            // 进行初始设置
            ConfigManager.init();
            ConfigManagerEx.init();
            // 获取PL配置文件信息
            CommonConfigData log4jConf = ConfigManagerEx.getCommonConfigData();
            // 设置Log4j
            configureLog4j(log4jConf.getLog4jProperties());
            // 获取和检查各设置值
            ConfigManager.getProperties();
            ConfigManagerEx.getProperties();
            // 初始化EJB调用流量控制的信号量
            HttpCallHandler.initSemaphore();
        } catch (SNetConfigurationException e) {
            // 如果在记录器初始化之前发生异常，将不会输出
            snqtlog.outputSnqtLog("", SnqtLogIdEx.SNQT0002);
            System.exit(1);
        } catch (Exception e) {
            // 如果在记录器初始化之前发生异常，将不会输出
            snqtlog.outputSnqtLog("", SnqtLogIdEx.SNQT0002);
            System.exit(1);
        }
        try {
            CommonConfigData commonConfig = ConfigManagerEx.getCommonConfigData();
            // 初始化记录器
            LoggerFactoryEx.init(ConfigManagerEx.getBusinessNoList(), commonConfig.getJournallogIgnoreIp());
            // 创建通道组
            ChannelGroup channelGroup = new DefaultChannelGroup(CHANNEL_GROUP_NAME);
            // 获取代表业务号列表
            List<String> repBusinessNoList = commonConfig.getRepBusinessNoList();
            // 获取节点号
            Integer nodeNo = commonConfig.getNodeNo(commonConfig.getHostName());
            // 为每个代表业务号生成ServerBootstrap
            for (String repBusNo : repBusinessNoList) {
                ConfigDataEx config = ConfigManagerEx.getConfigData(repBusNo);
                bootstrapList.add(bind(channelGroup, config, repBusNo, nodeNo));
            }
            snqtlog.outputSnqtLog("", SnqtLogIdEx.SNQT0001);
            ShutdownHookListener shutdownHookListener = new ShutdownHookListener();
            Runtime.getRuntime().addShutdownHook(shutdownHookListener);
            shutdownHookListener.waitShutdownHook();
            ChannelGroupFuture channelGroupFuture = channelGroup.close();
            channelGroupFuture.awaitUninterruptibly();
            for (ServerBootstrap bootstrap : this.bootstrapList) {
                bootstrap.getFactory().releaseExternalResources();
            }
            snqtlog.outputSnqtLog("", SnqtLogIdEx.SNQT0003);
        } catch (Exception e) {
            snqtlog.outputSnqtLog("", SnqtLogIdEx.SNQT0020, SNetProxyUtil.getStackTrace(e));
            snqtlog.outputSnqtLog("", SnqtLogIdEx.SNQT0002);
            System.exit(1);
        }
    }

    /**
     * 为每个代表业务号生成ServerBootstrap.
     * <p>
     * 1. 创建ServerBootstrap。<br>
     * 2. 创建管道。<br>
     * 3. 如果指定了TCP发送缓冲区大小，则进行设置。<br>
     * 4. 绑定ServerBootstrap并添加到ChannelGroup中。<br>
     * 5. 如果指定了TCP后台日志记录大小，则进行设置。<br>
     * 6. 如果指定了TCP接收缓冲区大小，则进行设置。<br>
     * </p>
     * 
     * @param channelGroup 通道组
     * @param config 代表业务设置文件信息
     * @param repBusNo 代表业务号
     * @param nodeNo 节点号
     * @return ServerBootstrap
     */
    private static ServerBootstrap bind(ChannelGroup channelGroup, ConfigDataEx config, String repBusNo, Integer nodeNo) {
        // 用于T2和T10的计时器
        Timer timer = new HashedWheelTimer();
        // 创建ServerBootstrap
        ServerBootstrap bootstrap = new ServerBootstrap(
                new OioServerSocketChannelFactory(
                        Executors.newFixedThreadPool(BOSS_EXECUTOR_MAX_THREAD_NUM),
                        Executors.newFixedThreadPool(config.getMaxThread())));
        // 创建管道
        bootstrap.setPipelineFactory(new ServerPipelineFactoryEx(timer, repBusNo, nodeNo));
        // 设置TCP后台日志记录大小
        if (config.getTcpBacklog() >= 0) {
            bootstrap.setOption(TCP_BACKLOG_KEY, config.getTcpBacklog());
        }
        // 设置TCP接收缓冲区大小
        if (config.getTcpRcvBufferSize() >= 0) {
            bootstrap.setOption(TCP_RECEIVE_BUFFER_SIZE_KEY, config.getTcpRcvBufferSize());
        }
        // 设置TCP发送缓冲区大小
        if (config.getTcpSendBufferSize() >= 0) {
            bootstrap.setOption(TCP_SEND_BUFFER_SIZE_KEY, config.getTcpSendBufferSize());
        }
        Channel channel = bootstrap.bind(new InetSocketAddress(config.getTcpListenPort()));
        channelGroup.add(channel);
        return bootstrap;
    }

    /**
     * 设置Log4j.
     * <p>
     * 由于Log4j设置失败时无法进行错误处理，因此暂时更改标准错误输出以执行错误处理。<br>
     * 1. 保存原始的标准错误输出目标（PrintStream对象）。<br>
     * 2. 将标准错误输出目标更改为错误处理用的对象。<br>
     * 3. 进行Log4j的设置。<br>
     * 4. 将标准错误输出目标更改回保存的原始对象。<br>
     * 5. 引用错误处理对象，检查是否发生错误。<br>
     * 6. 如果发生错误，则在标准错误输出后抛出SNetConfigurationException。<br>
     * <br>
     * 异常：如果Log4j设置失败，则会抛出SNetConfigurationException。
     * </p>
     * 
     * @param log4jProperties Log4j配置文件路径
     * @throws SNetConfigurationException Log4j设置失败时抛出
     */
    private void configureLog4j(String log4jProperties) throws SNetConfigurationException {
        // 保存原始对象
        PrintStream orgStream = System.err;
        // 更改为错误处理用的对象
        FileOutputStream fos = new FileOutputStream(FileDescriptor.err);
        DummyPrintStream stream = new DummyPrintStream(new BufferedOutputStream(fos, 128), true);
        System.setErr(stream);
        // 进行Log4j的设置
        PropertyConfigurator.configure(log4jProperties);
        // 将标准错误输出目标更改回保存的原始对象
        System.setErr(orgStream);
        // 如果发生错误
        if (stream.isError()) {
            // 在标准错误输出中输出错误消息
            for (String message : stream.getErrorMessage()) {
                System.err.println(message); // @NOPMD(JavaLoggingSystemPrintln)
            }
            // 抛出异常以停止启动
            throw new SNetConfigurationException();
        }
    }
}
//该代码是一个Java程序，作为一个简单的HTTP代理服务器的启动类。以下是各个功能模块的解释、调用时机和数据传递：
//
//1. `main` 方法：
//   - **功能模块：** 作为程序的入口点，创建 `SNetHttpProxyServer` 实例，并调用其 `run` 方法。
//   - **调用时机：** 在程序启动时。
//   - **数据传递：** 无。
//
//2. `run` 方法：
//   - **功能模块：** 执行程序的启动过程，包括初始化配置、设置日志、创建代表业务的 `ServerBootstrap` 实例等。
//   - **调用时机：** 在 `main` 方法中调用。
//   - **数据传递：** 从配置管理器 (`ConfigManager` 和 `ConfigManagerEx`) 获取配置信息，将日志配置信息传递给 `configureLog4j` 方法。
//
//3. `configureLog4j` 方法：
//   - **功能模块：** 设置 Log4j 日志系统的配置，通过捕获标准错误输出进行错误处理。
//   - **调用时机：** 在 `run` 方法中被调用。
//   - **数据传递：** 通过参数传递 Log4j 配置文件的路径。
//
//4. `bind` 方法：
//   - **功能模块：** 为每个代表业务号生成 `ServerBootstrap` 实例，设置相关参数。
//   - **调用时机：** 在 `run` 方法中的循环中被调用。
//   - **数据传递：** 从配置管理器获取业务号相关配置信息，传递给 `ServerPipelineFactoryEx` 构造函数，然后返回 `ServerBootstrap` 实例。
//
//5. `ServerPipelineFactoryEx` 类：
//   - **功能模块：** 创建代表业务的 `ChannelPipeline`。
//   - **调用时机：** 在 `bind` 方法中被调用。
//   - **数据传递：** 从配置中获取的相关信息，例如定时器、业务号、节点号等。
//
//6. 其他成员变量和方法：
//   - `CHANNEL_GROUP_NAME`：通道组的名称。
//   - `TCP_BACKLOG_KEY`、`TCP_RECEIVE_BUFFER_SIZE_KEY`、`TCP_SEND_BUFFER_SIZE_KEY`：TCP 相关参数的关键字。
//   - `BOSS_EXECUTOR_MAX_THREAD_NUM`：父执行器的最大线程数。
//   - `snqtlog`：SNQT记录器实例。
//   - `bootstrapList`：代表业务号的 `ServerBootstrap` 列表。
//   - ...
//
//以上是对该代码中各个功能模块、调用时机和数据传递的简要解释。如果你对特定部分有更详细的疑问，可以进一步提问。