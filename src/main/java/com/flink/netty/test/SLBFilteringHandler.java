package com.flink.netty.test;

/**
 * <P>
 * SLBからのヘルスチェックを判別するハンドラー
 * </P>
 *
 * @author niwa
 */
public class SLBFilteringHandler extends SimpleChannelUpstreamHandler {
    /** 代表業務No */
    private String repBusinessNo;
    /**
     * コンストラクタ
     *
     * @param repBusinessNo 代表業務No
     */
    public SLBFilteringHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.channelOpen(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void channelBound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.channelBound(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.channelConnected(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.messageReceived(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.writeComplete(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.channelDisconnected(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void channelUnbound(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.channelUnbound(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックの場合は、後続のハンドラにイベントを伝播しないためのフィルター<br>
     * SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
// SLBのヘルスチェック以外の場合のみ、後続のハンドラにイベントを伝播する
        if (!isSLBHealthcheck(e.getChannel())) {
            super.channelClosed(ctx, e);
        }
    }
    /**
     * SLBのヘルスチェックか判別する
     *
     * @param channel チャネル
     * @return 判別結果（true：SLBのヘルスチェック。false：それ以外）
     */
    private boolean isSLBHealthcheck(Channel channel) {
        String remoteAddress = SNetProxyUtil.getIpAddress(channel.getRemoteAddress().toString());
        ConfigData config = ConfigManager.getConfigData(this.repBusinessNo);
        List<String> ignoreIpList = config.getIgnoreIpList();
// SLBのヘルスチェックの場合
        if (ignoreIpList != null && ignoreIpList.contains(remoteAddress)) {
            return true;
        }
        return false;
    }
}
