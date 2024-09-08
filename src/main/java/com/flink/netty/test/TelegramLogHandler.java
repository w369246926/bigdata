package com.flink.netty.test;

import java.nio.channels.Channel;
import java.util.logging.LogManager;

/**
 * <P>
 * 用于输出电文日志的处理器
 * </P>
 * 
 * 该处理器负责记录接收和发送的电文日志。在数据传输过程中，会分别记录请求电文和响应电文的日志信息。
 * </P>
 * 
 * 通过实现 Netty 的 SimpleChannelHandler 类，处理与通道（Channel）相关的事件和操作。
 * 在 Netty 中，处理器的每个方法对应于不同的事件，如通道打开、接收到消息、写入消息等。
 * </P>
 * 
 * 该处理器会在通道打开时设置电文日志相关的信息，并在接收到消息（请求电文）和写入消息（响应电文）时记录电文日志。
 * 具体的电文日志信息包括消息ID、服务名称、业务号、业务名、输出处理、以及其他相关信息。
 * </P>
 * 
 * 电文日志记录过程中，还会处理可能发生的异常情况，例如电文日志输出失败，会记录详细的异常信息和相关的堆栈信息。
 * </P>
 * 
 * 该处理器在整个处理流程中的位置是在数据传输的最后阶段，即接收和发送电文后，用于记录日志。
 * </P>
 * 
 * 数据的传递是通过 Netty 的 ChannelPipeline 实现的，每个处理器负责特定的任务，数据按照添加的顺序经过整个处理流程。
 * </P>
 * 
 * @author niwa
 */
public class TelegramLogHandler extends SimpleChannelHandler {
    /** 代表业务号 */
    private String repBusinessNo;

    /**
     * 构造方法
     * 
     * @param repBusinessNo 代表业务号
     */
    public TelegramLogHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 在通道打开时设置日志记录器
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 获取连接的远程IP地址，获取电文日志专用的日志记录器，并将其设置到SessionData（ChannelLocal）中
        String ipaddr = SNetProxyUtil.getIpAddress(e.getChannel().getRemoteAddress().toString());
        SessionData.setTelegramLogger(e.getChannel(), LoggerFactory.getTelegramLogger(ipaddr));
        super.channelOpen(ctx, e);
    }

    /**
     * 处理接收到的请求电文，记录电文日志
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 设置电文日志信息
        setRequestTelegramLogData(e.getChannel());
        try {
            // 记录电文日志
            LogManager.outputTelegramLog(e.getChannel(), this.repBusinessNo);
        } catch (Exception ex) {
            // 电文日志记录失败时处理
            TelegramLogData telegramLogData = SessionData.getTelegramLogData(e.getChannel());
            // 记录SNQT日志和SNQT详细日志（包括堆栈跟踪和电文HexDump）
            LogManager.outputSnqtLog(e.getChannel(), SnqtLogId.SNQT1012);
            LogManager.outputSnqtDetailLog(e.getChannel(), LogLevel.ERROR,
                    ToStringBuilder.reflectionToString(telegramLogData), ex, this.repBusinessNo);
            LogManager.outputSnqtDetailLog(e.getChannel(), LogLevel.ERROR,
                    SNetProxyUtil.getHexDump(telegramLogData.getTelegram()), this.repBusinessNo);
        }
        super.messageReceived(ctx, e);
    }

    /**
     * 处理写入的响应电文，记录电文日志
     */
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 设置电文日志信息
        setResponseTelegramLogData(e.getChannel());
        try {
            // 记录电文日志
            LogManager.outputTelegramLog(e.getChannel(), this.repBusinessNo);
        } catch (Exception ex) {
            // 电文日志记录失败时处理
            TelegramLogData telegramLogData = SessionData.getTelegramLogData(e.getChannel());
            // 记录SNQT日志和SNQT详细日志（包括堆栈跟踪和电文HexDump）
            LogManager.outputSnqtLog(e.getChannel(), SnqtLogId.SNQT1012);
            LogManager.outputSnqtDetailLog(e.getChannel(), LogLevel.ERROR,
                    ToStringBuilder.reflectionToString(telegramLogData), ex, this.repBusinessNo);
            LogManager.outputSnqtDetailLog(e.getChannel(), LogLevel.ERROR,
                    SNetProxyUtil.getHexDump(telegramLogData.getTelegram()), this.repBusinessNo);
        }
        super.writeRequested(ctx, e);
    }

    /**
     * 设置请求电文的电文日志信息
     * 
     * @param channel 通道
     */
    private void setRequestTelegramLogData(Channel channel) {
        // 获取业务号
        String businessNo = SessionData.getBusinessNo(channel);
        BusinessConfigData businessConfig = ConfigManager.getBusinessConfigData(this.repBusinessNo, businessNo);
        TelegramLogData data = SessionData.getTelegramLogData(channel);
        // 设置电文日志信息
        data.setMsgid(TelegramLogMsgId.TOIAWASE_YOUKYU.getValue());
        // 不设置返回代码
        data.setServnm(formatHostName(businessConfig.getHostName()));
        data.setGmno(businessNo);
        data.setGmname(businessConfig.getBusinessName());
        // 端末名已设置
        data.setOutproc(businessConfig.getTelegramlogOutputProcess());
        // 日志关联信息已设置
        // 询问电文已设置
    }

    /**
     * 设置响应电文的电文日志信息
     * 
     * @param channel 通道
     */
    private void setResponseTelegramLogData(Channel channel) {
        TelegramLogData data = SessionData.getTelegramLogData(channel);
        // 获取业务号
        String businessNo = SessionData.getBusinessNo(channel);
        BusinessConfigData businessConfig = ConfigManager.getBusinessConfigData(this.repBusinessNo, businessNo);
        if (businessConfig == null) {
            // 如果在请求电文解码完成前发生错误，将无法获取业务号
            // 从CommonConfigData中获取处理服务器名
            CommonConfigData commonConfig = ConfigManager.getCommonConfigData();
            data.setServnm(formatHostName(commonConfig.getHostName()));
            LogManager.outputSnqtDetailLog(channel, LogLevel.DEBUG,
                    "BusinessConfigData is null. repBusinessNo = " + this.repBusinessNo + ", businessNo = " + businessNo,
                    this.repBusinessNo);
        } else {
            data.setServnm(formatHostName(businessConfig.getHostName()));
            data.setGmname(businessConfig.getBusinessName());
            data.setOutproc(businessConfig.getTelegramlogOutputProcess());
        }
        data.setMsgid(TelegramLogMsgId.TOIAWASE_OUTOU.getValue());
        data.setGmno(businessNo);
        // 不设置返回代码
        // 端末名已设置
        // 日志关联信息已设置
        // 询问电文已设置
    }

    /**
     * 当主机名超过9个字符时，仅获取前8个字符
     * 
     * @param hostName 主机名
     * @return 编辑后的主机名
     */
    private String formatHostName(String hostName) {
        if (hostName == null || hostName.length() < 9) {
            return hostName;
        }
        // 获取前8个字符
        return hostName.substring(0, 8);
    }
}

