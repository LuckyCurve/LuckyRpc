package cn.luckycurve.client.dispatch;

import cn.luckycurve.common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

/**
 * @author LuckyCurve
 */
@AllArgsConstructor
public class ResponseDispatchHandler extends SimpleChannelInboundHandler<RpcResponse> {

    ResponseDispatchCenter center;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse result) throws Exception {
        center.set(result.getHeader().getStreamId(), result);
    }
}
