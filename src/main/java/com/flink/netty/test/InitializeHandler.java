package com.flink.netty.test;

package jp.co.sej.snet.proxy.handler.init;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.sej.snet.proxy.channellocal.SessionData;
import jp.co.sej.snet.proxy.log.LogData;
import jp.co.sej.snet.proxy.log.LogManager;
import jp.co.sej.snet.proxy.log.LoggerFactory;
import jp.co.sej.snet.proxy.util.SNetProxyUtil;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * <P>
 * 执行各种初始化操作的处理器
 * </P>
 * 
 * @author niwa
 */
public class InitializeHandler extends SimpleChannelUpstreamHandler {
    /** 节点编号 */
    private Integer nodeNo;
    /** 代表业务编号 */
    private String repBusinessNo;

    /**
     * 构造函数
     * 
     * @param nodeNo           节点编号
     * @param repBusinessNo    代表业务编号
     */
    public InitializeHandler(Integer nodeNo, String repBusinessNo) {
        this.nodeNo = nodeNo;
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 执行各种初始化操作.
     * <p>
     * ・获取连接目标的IP地址并获取用于记录会话日志的Logger。获取的Logger会设置到ChannelLocal中<br>
     * ・初始化LogData对象<br>
     * ・设置日志关联键
     * </p>
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // 获取连接目标的IP地址并获取用于记录会话日志的Logger。获取的Logger会设置到ChannelLocal中
        String ipaddr = SNetProxyUtil.getIpAddress(e.getChannel().getRemoteAddress().toString());
        SessionData.setSessionLogger(e.getChannel(), LoggerFactory.getSessionLogger(ipaddr));
        
        // 初始化LogData
        LogData logData = new LogData();
        // 设置日志关联键
        logData.setLogLinkKey(createLogLinkKey(e.getChannel()));
        SessionData.setLogData(e.getChannel(), logData);
        
        super.channelOpen(ctx, e);
    }

    /**
     * 删除ChannelLocal中的数据
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 删除ChannelLocal中与该连接相关的数据
        SessionData.removeSessionData(e.getChannel());
        
        super.channelClosed(ctx, e);
    }

    /**
     * 生成日志关联键.
     * <p>
     * 如果Channel ID为负值，则将其转换为对应的正值以获取unsigned int的值。<br>
     * </p>
     * 
     * @param channel    通道
     * @return 日志关联键
     */
    private String createLogLinkKey(Channel channel) {
        long channelId = channel.getId().longValue();
        String id = null;
        // 如果Channel ID为负值，则将其转换为正值
        if (channelId < 0) {
            long unsignedChannelId = (channelId * -1L) + Integer.MAX_VALUE;
            id = String.format("%010d", unsignedChannelId);
        } else {
            id = String.format("%010d", channelId);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer key = new StringBuffer();
        key.append(String.format("%02d", this.nodeNo));
        key.append(id);
        key.append(formatter.format(new Date()));
        return key.toString();
    }
}

