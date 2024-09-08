package com.flink.netty.test;

import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.logging.LogManager;

/**
 * <p>
 * 负责解码请求电文的处理器
 * </p>
 * 
 * 作者：niwa
 */
public class TelegramDecodeHandler extends SimpleChannelUpstreamHandler {
    /** 代表业务号 */
    private String repBusinessNo;
    /** 处理业务号 */
    private String businessNo;
    /** 终端号 */
    private String termNo;

    /**
     * 构造函数
     * 
     * @param repBusinessNo 代表业务号
     */
    public TelegramDecodeHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 将电文头解码为Telegram类型，并将其设置到TelegramItem的头部和体部。<br>
     * <p>
     * ・将电文头解码为Telegram类型，并将其设置到TelegramItem的头部和体部<br>
     * ・如果解码失败（从解码器抛出TelegramDecodingException，IllegalArgumentException异常），则输出SNQT日志，
     * 输出SNQT详细日志并以HEX格式转储，然后将结束码、结束码详细信息和错误检测位置设置到会话日志信息中并结束处理。<br>
     * <blockquote> [会话日志输出内容]<blockquote> ・结束码：9<br>
     * ・结束码详细信息："RECVERR "<br>
     * ・错误检测位置："R"<br>
     * </blockquote></blockquote> ・将处理业务号设置到通道本地<br>
     * ・解码后，检查电文内容，如果出现以下错误，则输出SNQT日志，输出SNQT详细日志并以HEX格式转储，
     * 然后将结束码、结束码详细信息和错误检测位置设置到会话日志信息中并结束处理。<br>
     * <br>
     * <blockquote>[错误类型]<blockquote> ・文本长度错误：接收到的电文记录长度与接收到的电文大小不同时<br>
     * ・电文区分错误："20"以外的情况<br>
     * ・业务区分错误：与配置文件中记录的处理业务不同时<br>
     * </blockquote> [会话日志输出内容]<blockquote> ・结束码：5<br>
     * ・结束码详细信息："DENCHKNG"<br>
     * ・错误检测位置："R"<br>
     * </blockquote></blockquote> ・将处理业务号设置到通道本地<br>
     * ・将处理业务号和业务名设置到会话日志信息中<br>
     * ・将终端号设置到会话日志信息和电文日志信息中<br>
     * </p>
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        byte[] dataArray = (byte[]) e.getMessage();
        TelegramItem<Telegram> item = null;
        try {
            // 将电文头解码为Telegram类型，并将其设置到TelegramItem的头部和体部
            item = decodeTelegram(dataArray);
        } catch (TelegramDecodingException ex) {
            // 处理错误
            doDecodeErrorHandling(e.getChannel(), dataArray, ex);
            // 结束
            return;
        } catch (IllegalArgumentException ex) {
            // 处理错误
            doDecodeErrorHandling(e.getChannel(), dataArray, ex);
            // 结束
            return;
        }
        // 检查电文内容
        if (!checkData(dataArray, item, e.getChannel())) {
            // 如果出现错误，则结束
            return;
        }
        // 将处理业务号设置到通道本地
        SessionData.setBusinessNo(e.getChannel(), this.businessNo);
        // 将处理业务号和业务名设置到会话日志信息中
        BusinessConfigData config = ConfigManager.getBusinessConfigData(this.repBusinessNo, this.businessNo);
        SessionLogData sessionLogData = SessionData.getSessionLogData(e.getChannel());
        sessionLogData.setGmno(this.businessNo);
        sessionLogData.setGmname(config.getBusinessName());
        // 将终端号设置到会话日志信息和电文日志信息中
        if (this.termNo != null) {
            sessionLogData.setTermno(this.termNo);
            TelegramLogData telegramLogData = SessionData.getTelegramLogData(e.getChannel());
            telegramLogData.setTermno(this.termNo);
        }
        // 将TelegramItem设置到MessageEvent中
        MessageEvent event = new UpstreamMessageEvent(e.getChannel(), item, e.getRemoteAddress());
        ctx.sendUpstream(event);
    }

    /**
     * 将电文头解码为Telegram类型，并将其设置到TelegramItem的头部和体部。<br>
     * <p>
     * 在生死监视的情况下，使用SNetTelegramHeader进行解码<br>
     * 在非生死监视的情况下，使用SNetExtensionTelegramHeader进行解码<br>
     * </p>
     * 
     * @param dataArray 电文信息
     * @return 解码后的电文信息
     */
    private TelegramItem<Telegram> decodeTelegram(byte[] dataArray) {
        TelegramItem<Telegram> item = new TelegramItem<Telegram>();
        // 在生死监视的情况下
        if (dataArray.length == Constant.HEALTH_CHECK_BYTE_SIZE) {
            // 解码电文头
            SNetTelegramHeader header = (SNetTelegramHeader) TelegramDecoder
                    .decode(dataArray, SNetTelegramHeader.class);
            item.setHeader(header);
            item.setBody(Arrays.copyOfRange(dataArray, SNetProxyUtil.getTotalByteSize(header), dataArray.length));
            // 设置处理业务号
            this.businessNo = SNetProxyUtil.suppressZeroBusinessNo(header.getBusinessDivision());
        } else {
            // 解码电文头
            SNetExtensionTelegramHeader header = (SNetExtensionTelegramHeader) TelegramDecoder
                    .decode(dataArray, SNetExtensionTelegramHeader.class);
            item.setHeader(header);
            item.setBody(Arrays.copyOfRange(dataArray, SNetProxyUtil.getTotalByteSize(header), dataArray.length));
            // 设置处理业务号
            this.businessNo = SNetProxyUtil.suppressZeroBusinessNo(header.getBusinessDivision());
            // 设置终端号
            StringBuffer buffer = new StringBuffer();
            buffer.append(header.getCompanyId());
            buffer.append(header.getShopNo());
            this.termNo = buffer.toString();
        }
        return item;
    }

    /**
     * 检查请求电文的头部值。<br>
     * <p>
     * 检查文本长度错误、电文区分错误、业务区分错误。<br>
     * </p>
     * 
     * @param dataArray 请求电文字节数组
     * @param item 解码后的请求电文
     * @param channel 通道
     * @return 检查结果（true=OK，false=NG）
     */
    private boolean checkData(byte[] dataArray, TelegramItem<Telegram> item, Channel channel) {
        boolean result = false;
        SNetTelegramHeader header = (SNetTelegramHeader) item.getHeader();
        if (dataArray.length != header.getTelegramLength()) {
            // 如果发生文本长度错误
            LogManager.outputSnqtLog(channel, SnqtLogId.SNQT1003, header.getTelegramLength(), dataArray.length);
            doTelegramCheckErrorHandling(channel, dataArray);
            // 返回错误响应
            Channels.write(channel, ErrorResponseType.TELEGRAM_LENGTH_ERROR);
        } else if (!TelegramSegment.REQUEST.getValue().equals(header.getTelegramSegment())) {
            // 如果发生电文区分错误
            LogManager.outputSnqtLog(channel, SnqtLogId.SNQT1004, header.getTelegramSegment());
            doTelegramCheckErrorHandling(channel, dataArray);
            // 返回错误响应
            Channels.write(channel, ErrorResponseType.TELEGRAM_SEGMETNT_ERROR);
        } else if (ConfigManager.getBusinessConfigData(this.repBusinessNo, this.businessNo) == null) {
            // 如果发生业务区分错误（无法获取配置文件时，将其视为错误）
            LogManager.outputSnqtLog(channel, SnqtLogId.SNQT1005, header.getBusinessDivision());
            doTelegramCheckErrorHandling(channel, dataArray);
            // 返回错误响应
            Channels.write(channel, ErrorResponseType.BUSINESS_DIVISION_ERROR);
        } else {
            // 如果没有错误
            result = true;
        }
        return result;
    }

    /**
     * 如果发生电文检查错误，执行相应处理。<br>
     * <p>
     * 1.以HEX格式转储整个电文并输出。<br>
     * 2.将结束码、结束码详细信息和错误检测位置设置到会话日志信息中。<br>
     * </p>
     * 
     * @param channel 通道
     * @param dataArray 解码前的电文
     */
    private void doTelegramCheckErrorHandling(Channel channel, byte[] dataArray) {
        // HEX格式转储整个电文。由于无法确定业务号（未确认），因此在代表业务号的SNQT详细日志中输出。
        LogManager
                .outputSnqtDetailLog(channel, LogLevel.ERROR, SNetProxyUtil.getHexDump(dataArray), this.repBusinessNo);
        // 将结束码、结束码详细信息和错误检测位置设置到会话日志信息中
        SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
        sessionLogData.setEndcode(SessionLogEndCodeDetail.DENCHKNG.getEndCodeValue());
        sessionLogData.setEndcodedetail(SessionLogEndCodeDetail.DENCHKNG.name());
        sessionLogData.setErrorplace(SessionLogEndCodeDetail.DENCHKNG.getErrorPlaceValue());
    }

    /**
     * 如果在解码过程中发生错误，执行相应处理。<br>
     * <p>
     * 1.输出错误日志。<br>
     * 2.以HEX格式转储整个电文并输出。<br>
     * 3.将结束码、结束码详细信息和错误检测位置设置到会话日志信息中。<br>
     * 4.返回解码错误响应。
     * </p>
     * 
     * @param channel 通道
     * @param dataArray 解码前的电文
     * @param ex 异常
     */
    private void doDecodeErrorHandling(Channel channel, byte[] dataArray, Exception ex) {
        // 输出错误日志
        LogManager.outputSnqtLog(channel, SnqtLogId.SNQT1002);
        // 以HEX格式转储整个电文并输出
        LogManager.outputSnqtDetailLog(	channel, LogLevel.ERROR, SNetProxyUtil.getHexDump(dataArray), ex,
                this.repBusinessNo);
        // 将结束码、结束码详细信息和错误检测位置设置到会话日志信息中
        SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
        sessionLogData.setEndcode(SessionLogEndCodeDetail.RECVERR.getEndCodeValue());
        sessionLogData.setEndcodedetail(SessionLogEndCodeDetail.RECVERR.name());
        sessionLogData.setErrorplace(SessionLogEndCodeDetail.RECVERR.getErrorPlaceValue());
        // 返回解码错误响应
        Channels.write(channel, ErrorResponseType.DECODE_ERROR);
    }
}
//这个类是一个Netty框架中的SimpleChannelUpstreamHandler，用于处理接收到的请求电文。在messageReceived方法中，它执行以下主要功能：
//
//日志输出： 输出追踪日志，记录当前业务的代表业务号。
//电文解码： 调用decodeTelegram方法将接收到的字节数组解码为TelegramItem对象，其中包含了电文的头部和体部。
//电文内容检查： 调用checkData方法检查解码后的电文头部的值，包括文本长度、电文区分、业务区分等错误。
//设置本地数据和日志信息： 如果检查通过，将处理业务号设置到通道本地，同时设置业务号、业务名和终端号到会话日志信息中。
//将TelegramItem设置到MessageEvent中： 创建一个新的UpstreamMessageEvent，将解码后的TelegramItem设置到其中，然后通过上下文将其发送给下一个处理器。
//通过ctx.sendUpstream(event)将event发送给ChannelPipeline的下一个处理器。这个事件会在ChannelPipeline中向上传播（Upstream），直到到达最后一个处理器或者某个处理器中调用了ctx.sendUpstream方法将其拦截下来。
//
//在Netty中，ChannelPipeline是一个处理事件的处理器链。当一个事件被触发，它会在这个链上向上或向下传播，直到被处理或者到达链的两端。这样，数据会在ChannelPipeline的不同处理器之间流动。