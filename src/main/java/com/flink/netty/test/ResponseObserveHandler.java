package com.flink.netty.test;

package jp.co.sej.snet.proxy.handler.observer;
import java.util.Date;
import jp.co.sej.snet.proxy.config.ConfigData;
import jp.co.sej.snet.proxy.config.ConfigManager;
import jp.co.sej.snet.proxy.log.LogManager;
import jp.co.sej.snet.proxy.log.constant.SnqtLogId;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * T3超时和处理时间（响应时间）观察处理器
 * 用于测量T3超时和处理时间，并输出SNQT日志。
 * 作者：niwa
 */
public class ResponseObserveHandler extends SimpleChannelHandler {
    /** 代表业务号 */
    private String repBusinessNo;
    
    /** 连接建立时间 */
    private Date connectedTime;
    
    /**
     * 构造方法
     * @param repBusinessNo 代表业务号
     */
    public ResponseObserveHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }
    
    /**
     * 获取连接建立时的时间
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        // 设置连接建立时的时间
        this.connectedTime = new Date();
        super.channelOpen(ctx, e);
    }
    
    /**
     * 根据连接建立时间和响应时间计算处理时间，
     * 如果超过T3超时或限定时间，则输出SNQT日志
     */
    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        LogManager.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);
        
        // 连接建立时间（毫秒）
        long connectedTime = this.connectedTime.getTime();
        // 响应时间（毫秒）
        long responseTime = new Date().getTime();
        // 处理时间（秒）
        long processingTime = (responseTime - connectedTime) / 1000;
        
        ConfigData config = ConfigManager.getConfigData(this.repBusinessNo);
        if (processingTime >= config.getT3Timer()) {
            // 如果超过T3超时
            // 输出错误日志
            LogManager.outputSnqtLog(e.getChannel(), SnqtLogId.SNQT2002, processingTime);
        } else if (processingTime >= config.getResponseWarnTime()) {
            // 如果超过限定时间
            // 输出错误日志
            LogManager.outputSnqtLog(e.getChannel(), SnqtLogId.SNQT2007, processingTime);
        }
        
        super.writeRequested(ctx, e);
    }
}
//这个类的主要功能是在处理数据写入请求时，计算连接的处理时间，并根据配置的阈值判断是否超时。如果处理时间超过了T3超时时间或者限定的响应时间阈值，就会输出相应的SNQT日志，用于记录和观察连接的响应时间是否符合预期。因此，可以将这个类总结为一个用于观察和记录连接响应时间的处理器。
