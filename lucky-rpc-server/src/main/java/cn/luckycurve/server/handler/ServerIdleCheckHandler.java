package cn.luckycurve.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LuckyCurve
 */
@Slf4j
public class ServerIdleCheckHandler extends IdleStateHandler {
    public ServerIdleCheckHandler() {
        super(10, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        // 触发了事件
        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            log.info("check read idle event, close channel for client");
            ctx.close();
            return;
        }

        super.channelIdle(ctx, evt);
    }
}
