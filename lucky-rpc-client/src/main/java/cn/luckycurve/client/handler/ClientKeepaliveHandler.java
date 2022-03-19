package cn.luckycurve.client.handler;

import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.keepalive.KeepaliveRequestBody;
import cn.luckycurve.util.IdUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LuckyCurve
 */
@Slf4j
public class ClientKeepaliveHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
            log.info("check write idle event, for keeping connection with server, send keepalive message");
            final RpcRequest request = new RpcRequest(IdUtil.next(), new KeepaliveRequestBody());
            ctx.writeAndFlush(request);
        }
        super.userEventTriggered(ctx, evt);
    }
}
