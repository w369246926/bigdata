package com.flink.netty.test;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.util.logging.LogManager;

/**
 * <P>
 * 集成电路编码处理器
 * </P>
 * 
 * @作者 niwa
 */
public class TelegramEncodeHandler extends SimpleChannelDownstreamHandler {
    /** 代表业务编号 */
    private String repBusinessNo;

    /**
     * 构造函数
     * 
     * @param repBusinessNo 代表业务编号
     */
    public TelegramEncodeHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 将电文头部从Telegram转换为字节数组，将整个电文的字节数组写入ChannelBuffer。
     * <p>
     * ・当消息为TelegramItem类型（而不是byte[]类型）时<br>
     * <blockquote> ・将头部编码为字节数组。<br>
     * ・如果发生TelegramEncodingException或IllegalArgumentException，输出SNQT日志/SNQT日志和会话日志，返回错误电文。<br>
     * <br>
     * [错误情况 - 会话日志输出内容]<blockquote> ・结束代码：Z<br>
     * ・结束代码详细信息："MRCVERR "<br>
     * ・错误检测位置："R"<br>
     * </blockquote><br>
     * ・如果主体部分有值，则连接头部和主体部分。 </blockquote><br>
     * ・当消息为字节数组类型时<br>
     * <blockquote> ・将字节数组作为响应电文。<br>
     * </blockquote> ・将响应电文设置到电文日志信息中<br>
     * ・更新会话日志信息的通信文本长度<br>
     * ・如果设置了请求电文的通信文本长度，则将响应电文的电文记录长度添加到其中<br>
     * </p>
     */
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 当调用e.getMessage()时，可以获得TelegramItem或byte[]类型
        // TelegramItem的body部分有时为null
        byte[] data = new byte[0];
        if (e.getMessage() instanceof TelegramItem<?>) {
            @SuppressWarnings("unchecked")
            TelegramItem<Telegram> item = (TelegramItem<Telegram>) e.getMessage();
            byte[] header = null;
            try {
                // 将头部编码为字节数组
                header = TelegramEncoder.encode(item.getHeader());
            } catch (TelegramEncodingException ex) {
                // 处理错误
                doEncodeErrorHandling(e.getChannel(), item, ex);
                // 结束
                return;
            } catch (IllegalArgumentException ex) {
                // 处理错误
                doEncodeErrorHandling(e.getChannel(), item, ex);
                // 结束
                return;
            }
            // 如果主体部分有值
            if (item.getBody() != null && item.getBody().length > 0) {
                // 连接头部和主体部分
                int size = header.length + item.getBody().length;
                ByteBuffer byteBuf = ByteBuffer.allocate(size);
                byteBuf.put(header);
                byteBuf.put(item.getBody());
                data = byteBuf.array();
            } else {
                data = header;
            }
        } else if (e.getMessage() instanceof byte[]) {
            // 如果是字节数组类型，则不执行任何操作
            data = (byte[]) e.getMessage();
        }
        ChannelBuffer buffer = ChannelBuffers.buffer(data.length);
        buffer.writeBytes(data);
        // 将响应电文设置到电文日志信息中
        TelegramLogData telegramLogData = SessionData.getTelegramLogData(e.getChannel());
        telegramLogData.setTelegram(data);
        // 更新会话日志信息的通信文本长度
        SessionLogData sessionLogData = SessionData.getSessionLogData(e.getChannel());
        // 如果设置了请求电文的通信文本长度，则将响应电文的电文记录长度添加到其中
        Integer requestTextLen = sessionLogData.getTextlen();
        if (requestTextLen != null) {
            sessionLogData.setTextlen(requestTextLen.intValue() + data.length);
        } else {
            sessionLogData.setTextlen(data.length);
        }
        Channels.write(ctx, e.getFuture(), buffer, e.getRemoteAddress());
    }

    /**
     * 处理编码过程中发生错误的情况
     * 
     * @param channel 通道
     * @param item 响应电文
     * @param ex 异常
     */
    private void doEncodeErrorHandling(Channel channel, TelegramItem<Telegram> item, Exception ex) {
        // 头部以文本形式输出，主体部分以HexDump形式输出
        StringBuffer buffer = new StringBuffer();
        buffer.append(item.getHeader().toString());
        buffer.append(", ");
        buffer.append(SNetProxyUtil.getHexDump(item.getBody()));
        // 输出错误日志
        LogManager.outputSnqtLog(channel, SnqtLogId.SNQT1011);
        LogManager.outputSnqtDetailLog(channel, LogLevel.ERROR, buffer.toString(), ex, this.repBusinessNo);
        // 设置结束代码、结束代码详细信息、错误检测位置到会话日志信息中
        SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
        sessionLogData.setEndcode(SessionLogEndCodeDetail.MRCVERR.getEndCodeValue());
        sessionLogData.setEndcodedetail(SessionLogEndCodeDetail.MRCVERR.name());
        sessionLogData.setErrorplace(SessionLogEndCodeDetail.MRCVERR.getErrorPlaceValue());
        // 返回错误响应
        Channels.write(channel, ErrorResponseType.ENCODE_ERROR);
    }
}

//该`TelegramEncodeHandler`类是一个用于编码电文的处理器，主要功能是将`Telegram`对象的头部部分转换为字节数组，并将整个电文的字节数组写入`ChannelBuffer`。以下是该类的功能模块、调用时机和数据传递的解释：
//
//### 功能模块：
//
//1. **接收`writeRequested`事件：** 通过继承`SimpleChannelDownstreamHandler`类，该处理器能够接收来自`writeRequested`事件。
//
//2. **判断消息类型：** 通过判断`e.getMessage()`的类型，确定消息是`TelegramItem`类型还是`byte[]`类型。
//
//3. **TelegramItem类型处理：** 如果消息是`TelegramItem`类型，执行以下步骤：
//   - 使用`TelegramEncoder`对头部进行编码，得到头部的字节数组。
//   - 如果头部和消息体非空，将它们连接成一个完整的电文字节数组。
//   - 如果发生`TelegramEncodingException`或`IllegalArgumentException`异常，执行错误处理逻辑，记录SNQT日志和会话日志，并返回错误响应。
//
//4. **byte[]类型处理：** 如果消息是`byte[]`类型，直接将其作为响应电文字节数组。
//
//5. **设置电文日志和会话日志：** 将响应电文字节数组设置到电文日志信息中，并更新会话日志的通信文本长度。
//
//6. **写入`ChannelBuffer`：** 将电文字节数组写入`ChannelBuffer`，并通过`Channels.write`方法写入`Channel`。
//
//### 调用时机：
//
//- 在调用`writeRequested`方法时，表示有数据要写入`Channel`，因此该方法在消息要写入时被调用。
//
//### 数据传递：
//
//- 通过`e.getMessage()`获取要写入的消息，根据消息类型进行相应处理。
//
//- 通过`TelegramEncoder`对`
//
//Telegram`对象的头部进行编码，处理异常时记录相关错误日志。
//
//- 将处理后的电文字节数组写入`ChannelBuffer`，并通过`Channels.write`方法写入`Channel`。
//
//- 更新会话日志的通信文本长度，记录电文日志的相关信息。
//
//- 在发生错误时，通过`doEncodeErrorHandling`方法执行错误处理逻辑，包括记录SNQT日志、会话日志，并返回错误响应。
//
//这个处理器的设计主要用于处理发送的电文，在写入`Channel`之前对电文进行编码操作。在编码过程中，如果发生异常，会进行相应的错误处理，记录相关的日志信息。
//




