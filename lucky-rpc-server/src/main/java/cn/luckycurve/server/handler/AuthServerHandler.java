package cn.luckycurve.server.handler;


import cn.luckycurve.common.MessageBody;
import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.auth.AuthRequestBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static cn.luckycurve.server.config.ServerConfig.PASSWORD;
import static cn.luckycurve.server.config.ServerConfig.USERNAME;


/**
 * @author LuckyCurve
 */
@Slf4j
public class AuthServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        final MessageBody body = request.getBody();
        if (body instanceof AuthRequestBody) {
            final AuthRequestBody authRequestBody = (AuthRequestBody) body;

            if (USERNAME.equalsIgnoreCase(authRequestBody.getUsername()) &&
                    PASSWORD.equalsIgnoreCase(authRequestBody.getPassword())) {
                log.info("auth passed!");
                ctx.channel().pipeline().remove(this);
            } else {
                log.error("auth failed! close channel");
                ctx.close();
            }
        } else {
            log.error("auth failed! close channel");
            ctx.close();
        }
    }
}
