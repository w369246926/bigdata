package com.flink.netty.test;

import java.nio.channels.Channels;

public class ServerPipelineFactoryEx implements ChannelPipelineFactory {

    /** タイマー */
    private final Timer timer;

    /** タイマー用ハンドラー */
    private final ChannelHandler idleStateHandler;

    /** 代表業務No */
    private final String repBusNo;

    /** ノードNo */
    private final Integer nodeNo;

    /** デバッグプロパティ名 */
    private final static String debugPipelineOptionProperty = "pipeline";

    /** デバッグプロパティの定義できる最大数 */
    private final static int debugPipelineMax = 10;

    /** デバッグプロパティのパイプライン追加種別 (AddAfter) */
    private final static String debugPipelineAddAfter = "AddAfter";

    /** デバッグプロパティのパイプライン追加種別(AddBefore) */
    private final static String debugPipelineAddBefore = "AddBefore";

    /** デバッグプロパティのパース文字 */
    private final static String debugPipelineParseString = ";";

    /**
     * 构造函数.
     * <p>
     * 1. 保存要传递给每个处理程序的值。<br>
     * 2. 创建IdleStateHandler并设置T2和T8计时器。<br>
     * </p>
     * 
     * @param timer 计时器
     * @param repBusNo 代表业务号
     * @param nodeNo 节点号
     */
    public ServerPipelineFactoryEx(Timer timer, String repBusNo, Integer nodeNo) {

        ConfigDataEx config = ConfigManagerEx.getConfigData(repBusNo);

        this.repBusNo = repBusNo;
        this.timer = timer;
        this.nodeNo = nodeNo;

        // readerIdleTimeSeconds: T2超时时间
        // allIdleTimeSeconds: T8超时时间
        this.idleStateHandler = new IdleStateHandler(this.timer, config.getT2Timer(), 0, config.getT8Timer());
    }

    /**
     * 生成管道.
     * <br>
     * 如果在系统属性中定义了调试处理程序，则将其集成到管道中。<br>
     * 格式：-Dpipeline[0-9]=[AddAfter/AddBefore];插入位置的处理程序名称;要插入的类（从包中指定）<br>
     * 例：-Dpipeline0=AddAfter;errorTelegramCreateHandler;jp.co.sej.snet.proxy.handler.test.TCPSessionJournalHandler
     */
    @Override
    public ChannelPipeline getPipeline() throws Exception {

        ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("slbFilteringHandler", new SLBFilteringHandler(this.repBusNo));
        pipeline.addLast("initializeHandler", new InitializeHandler(this.nodeNo, this.repBusNo));
        pipeline.addLast("responseObserveHandler", new ResponseObserveHandler(this.repBusNo));
        pipeline.addLast("sessionLogHandler", new SessionLogHandler(this.repBusNo));
        pipeline.addLast("telegramReceiver", new TelegramReceiver(this.repBusNo));
        pipeline.addLast("telegramDecodeHandler", new TelegramDecodeHandler(this.repBusNo));
        pipeline.addLast("telegramLogHandler", new TelegramLogHandler(this.repBusNo));
        pipeline.addLast("obstructionHandler", new ObstructionHandler(this.repBusNo));
        pipeline.addLast("idleStateHandler", idleStateHandler);
        pipeline.addLast("timeoutHandler", new TimeoutHandler(this.repBusNo));
        pipeline.addLast("telegramEncodeHandler", new TelegramEncodeHandler(this.repBusNo));
        pipeline.addLast("httpCallHandler", new HttpCallHandler(this.repBusNo));
        pipeline.addLast("errorTelegramCreateHandler", new ErrorTelegramCreateHandlerEx(this.repBusNo));

        // 集成调试处理程序
        for (int i = 0; i <= debugPipelineMax; i++) {
            String debugProperty = debugPipelineOptionProperty + i;

            String value = System.getProperty(debugProperty);

            if (value != null) {
                String values[] = value.split(debugPipelineParseString);

                if (values.length == 3) {
                    // 如果可以使用解析字符串分割为3部分，则集成调试处理程序

                    if (debugPipelineAddAfter.equals(values[0])) {
                        pipeline.addAfter(values[1], values[2], (ChannelHandler) (ClassLoader
                            .getSystemClassLoader().loadClass(values[2])).newInstance());

                    } else if (debugPipelineAddBefore.equals(values[0])) {
                        pipeline.addBefore(values[1], values[2], (ChannelHandler) (ClassLoader
                            .getSystemClassLoader().loadClass(values[2])).newInstance());

                    } else {
                        // 如果调试属性的记录存在问题，则抛出IllegalArgumentException
                        throw new IllegalArgumentException("调试属性添加类型不正确");
                    }

                } else {
                    // 如果调试属性的记录存在问题，则抛出IllegalArgumentException
                    throw new IllegalArgumentException("调试属性定义不正确");
                }
            }
        }

        return pipeline;
    }
}

