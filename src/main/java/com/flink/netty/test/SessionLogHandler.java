package com.flink.netty.test;

package jp.co.sej.snet.proxy.handler.sessionLog;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import jp.co.sej.snet.proxy.channellocal.SessionData;
import jp.co.sej.snet.proxy.config.ConfigData;
import jp.co.sej.snet.proxy.config.ConfigManager;
import jp.co.sej.snet.proxy.log.LogManager;
import jp.co.sej.snet.proxy.log.SessionLogData;
import jp.co.sej.snet.proxy.log.constant.SessionLogOutputTiming;
import jp.co.sej.snet.proxy.util.SNetProxyUtil;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * <P>
 * 输出会话日志的处理器
 * </P>
 * 
 * @author niwa
 */
public class SessionLogHandler extends SimpleChannelHandler {
    /** 代表业务号 */
    private String repBusinessNo;
    /** 报文接收状态。报文接收后变为true */
    private AtomicBoolean isMessageReceived = new AtomicBoolean(false);

    /**
     * 构造函数
     * 
     * @param repBusinessNo 代表业务号
     */
    public SessionLogHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 输出连接后的会话日志
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 设置会话日志信息
        setSessionLogDataSessionStart(e.getChannel());
        // 输出会话日志
        LogManager.outputSessionLog(e.getChannel(), this.repBusinessNo);
        super.channelConnected(ctx, e);
    }

    /**
     * 输出要求报文接收后的会话日志.
     * <p>
     * 如果要求报文被拆分为多个部分发送，则仅在第一次接收时输出会话日志。<br>
     * </p>
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 如果要求报文被拆分发送，则仅在第一次接收时输出会话日志
        if (!isMessageReceived.get()) {
            // 设置会话日志信息
            setSessionLogDataMessageReceive(e.getChannel());
            // 输出会话日志
            LogManager.outputSessionLog(e.getChannel(), this.repBusinessNo);
            this.isMessageReceived.set(true);
        }
        super.messageReceived(ctx, e);
    }

    /**
     * 输出响应报文发送前的会话日志
     */
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 设置会话日志信息
        setSessionLogDataMessageSend(e.getChannel());
        // 输出会话日志
        LogManager.outputSessionLog(e.getChannel(), this.repBusinessNo);
        super.writeRequested(ctx, e);
    }

    /**
     * 输出连接断开后的会话日志
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 设置会话日志信息
        setSessionLogDataSessionEnd(e.getChannel());
        // 输出会话日志
        LogManager.outputSessionLog(e.getChannel(), this.repBusinessNo);
        super.channelDisconnected(ctx, e);
    }

    /**
     * 设置会话开始时的会话日志信息
     * 
     * @param channel 通道
     */
    private void setSessionLogDataSessionStart(Channel channel) {
        ConfigData config = ConfigManager.getConfigData(this.repBusinessNo);
        SessionLogData data = SessionData.getSessionLogData(channel);
        data.setOutputTiming(SessionLogOutputTiming.SESSION_START.getValue());
        data.setSnodeno(Integer.toString(config.getNodeNo(config.getHostName())));
        data.setComstart(new Date());
        data.setIpaddr(SNetProxyUtil.getIpAddress(channel.getRemoteAddress().toString()));
        data.setServnm(config.getHostName());
        data.setTcpListenPort(new Integer(config.getTcpListenPort()));
    }

    /**
     * 设置报文接收时的会话日志信息
     * 
     * @param channel 通道
     */
    private void setSessionLogDataMessageReceive(Channel channel) {
        SessionLogData data = SessionData.getSessionLogData(channel);
        data.setOutputTiming(SessionLogOutputTiming.INQ_RECEIVE.getValue());
        data.setSesstart(new Date());
    }

    /**
     * 设置报文发送（响应）时的会话日志信息
     * 
     * @param channel 通道
     */
    private void setSessionLogDataMessageSend(Channel channel) {
        SessionLogData data = SessionData.getSessionLogData(channel);
        data.setOutputTiming(SessionLogOutputTiming.RES_SEND.getValue());
        data.setSesend(new Date());
    }

    /**
     * 设置会话结束时的会话日志信息
     * 
     * @param channel 通道
     */
    private void setSessionLogDataSessionEnd(Channel channel) {
        SessionLogData data = SessionData.getSessionLogData(channel);
        data.setOutputTiming(SessionLogOutputTiming.SESSION_END.getValue());
        // ##### 会话日志NULL处理 2016/3/24 #####
        // data.setComend(new Date());
        Date d = new Date();
        if (data.getSesstart() == null) {
            data.setSesstart(d);
        }
        if (data.getSesend() == null) {
            data.setSesend(d);
        }
        data.setComend(d);
        // ##### 会话日志NULL处理 2016/3/24 #####
    }
}

