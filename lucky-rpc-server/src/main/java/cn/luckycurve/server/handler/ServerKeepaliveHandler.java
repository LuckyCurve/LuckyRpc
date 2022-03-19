package cn.luckycurve.server.handler;

import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.keepalive.KeepaliveRequestBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author LuckyCurve
 */
public class ServerKeepaliveHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RpcRequest && ((RpcRequest) msg).getBody() instanceof KeepaliveRequestBody) {
            return;
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
