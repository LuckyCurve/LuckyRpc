package cn.luckycurve.server.handler;

import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.RpcResponse;
import cn.luckycurve.common.common.CommonResponseBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LuckyCurve
 */
@Slf4j
public class SimpleHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        log.info("read request message: {}", msg);

        final RpcResponse response = new RpcResponse();
        response.setHeader(msg.getHeader());
        response.setBody(new CommonResponseBody(1, "halo", null));

        ctx.writeAndFlush(response);
    }
}
