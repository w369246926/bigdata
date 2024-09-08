package com.flink.netty.test;

package jp.co.sej.snet.proxy.handler.decode;

import java.util.concurrent.atomic.AtomicBoolean;
import jp.co.sej.snet.proxy.channellocal.SessionData;
import jp.co.sej.snet.proxy.config.ConfigData;
import jp.co.sej.snet.proxy.config.ConfigManager;
import jp.co.sej.snet.proxy.constant.Constant;
import jp.co.sej.snet.proxy.log.LogManager;
import jp.co.sej.snet.proxy.log.SessionLogData;
import jp.co.sej.snet.proxy.log.TelegramLogData;
import jp.co.sej.snet.proxy.log.constant.SessionLogEndCodeDetail;
import jp.co.sej.snet.proxy.log.constant.SessionLogFileFormed;
import jp.co.sej.snet.proxy.log.constant.SessionLogTelegramResult;
import jp.co.sej.snet.proxy.log.constant.SnqtLogId;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * <P>
 * 等待接收所有数据，将 ChannelBuffer 的值转换为字节数组的处理程序
 * </P>
 * 
 * @author niwa
 */
public class TelegramReceiver extends FrameDecoder {
    /** 代表业务号 */
    private String repBusinessNo;
    /** 要求电文接收标志 */
    private AtomicBoolean isMessageReceived = new AtomicBoolean(false);

    /**
     * 构造方法
     * 
     * @param repBusinessNo 代表业务号
     */
    public TelegramReceiver(String repBusinessNo) {
        super();
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 构造方法
     * 
     * @param repBusinessNo 代表业务号
     * @param unfold
     */
    public TelegramReceiver(String repBusinessNo, boolean unfold) {
        super(unfold);
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 等待接收要求电文直至全部接收完成，将 ChannelBuffer 的值转换为字节数组
     * 
     * @param ctx     Channel 处理上下文
     * @param channel 当前通道
     * @param buffer  待处理的 ChannelBuffer
     * @return 处理后的字节数组，如果处理未完成则返回 null
     * @throws Exception 处理异常
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        LogManager.outputTraceLog(channel, Thread.currentThread().getStackTrace(), this.repBusinessNo);

        // 要求电文已接收，忽略处理
        if (this.isMessageReceived.get()) {
            return null;
        }

        // 未接收到电文长度字段，等待
        if (buffer.readableBytes() < Constant.TELEGRAM_LENGTH_BYTE_SIZE) {
            return null;
        }

        // 在读取电文长度字段之前标记当前缓冲区位置
        buffer.markReaderIndex();
        // 获取电文长度字段（读取4字节）
        int telegramLength = buffer.readInt();
        // 重置到标记的位置
        buffer.resetReaderIndex();

        ConfigData config = ConfigManager.getConfigData(this.repBusinessNo);

        // 电文长度超过最大配置值
        if (telegramLength > config.getTelegramMaxSize()) {
            // 输出错误日志
            LogManager.outputSnqtLog(channel, SnqtLogId.SNQT1001, config.getTelegramMaxSize(), telegramLength);

            // 设置会话日志信息
            SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
            sessionLogData.setEndcode(SessionLogEndCodeDetail.RECVERR.getEndCodeValue());
            sessionLogData.setEndcodedetail(SessionLogEndCodeDetail.RECVERR.name());
            sessionLogData.setErrorplace(SessionLogEndCodeDetail.RECVERR.getErrorPlaceValue());

            // 设置电文处理结果和文件成立类型
            sessionLogData.setTelresult(SessionLogTelegramResult.OTHER_ERROR.getIntValue());
            sessionLogData.setFileformed(SessionLogFileFormed.FAILURE.getValue());

            // 关闭并释放外部资源
            closeAndReleaseExternalResources(channel);
            return null;
        }

        // 电文未接收完成，等待
        if (buffer.readableBytes() < telegramLength) {
            return null;
        }

        // 读取字节数组
        byte[] dataArray = new byte[buffer.readableBytes()];
        buffer.readBytes(dataArray, 0, buffer.readableBytes());

        // 设置标志表示已接收到要求电文
        this.isMessageReceived.set(true);

        // 将要求电文设置到 ChannelLocal
        SessionData.setRequestTelegram(channel, dataArray);

        // 将要求电文设置到电文日志信息
        TelegramLogData telegramLogData = SessionData.getTelegramLogData(channel);
        telegramLogData.setTelegram(dataArray);

        // 将电文长度设置到会话日志信息
        SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
        sessionLogData.setTextlen(new Integer(dataArray.length));

        return dataArray;
    }

    /**
     * 关闭连接并释放外部资源（如果连接仍然处于连接状态）
     * 
     * @param channel 当前通道
     */
    private void closeAndReleaseExternalResources(Channel channel) {
        // 如果通道仍然连接，关闭连接
        if (channel.isConnected()) {
            channel.close();
        }
    }
}
//这个类继承了Netty的`FrameDecoder`类，这意味着它具备了解码帧（frame）的功能。`FrameDecoder`是一个抽象类，用于将`ChannelBuffer`解码为消息帧。
//
//下面是这个类的主要功能和实现逻辑：
//
//1. **等待接收所有数据：** `TelegramReceiver`会等待接收所有的数据，直到完全接收到要求的电文。
//2. **将`ChannelBuffer`的值转换为字节数组：** 在`decode`方法中，将`ChannelBuffer`的值解码为字节数组，表示一个完整的消息帧。
//3. **设置要求电文信息：** 将接收到的要求电文设置到`ChannelLocal`和电文日志信息中。
//4. **错误处理：** 在一些错误情况下，比如接收到的电文长度超过配置的最大值，会输出相应的错误日志，并关闭连接。
//
//这个类在什么情况下被调用以及如何向下传递数据的过程如下：
//
//- **调用时机：** 当有新的数据到达时，Netty会自动调用`decode`方法。这个方法在`ChannelPipeline`中的某个点被触发，通常是在数据入站时。
//- **向下传递数据：** 如果`decode`方法成功解码了一个完整的消息帧，它将返回相应的字节数组。这个字节数组会被传递到`ChannelPipeline`中的下一个`ChannelHandler`。在这个类中，数据被设置到了`ChannelLocal`和电文日志信息中，然后传递到下一个`ChannelHandler`。
//
//总体来说，`TelegramReceiver`的作用是确保接收完整的要求电文，并将相关信息设置到`ChannelLocal`和电文日志中，然后将数据传递到下一个处理器。
//这种方式确保了在同一`ChannelPipeline`中的不同处理器之间传递数据时的线程安全性，同时不需要显式的同步措施。这是Netty框架提供的一种方便而有效的机制。