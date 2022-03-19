package cn.luckycurve.client.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LuckyCurve
 */
@Slf4j
public class RpcFrameEncoder extends LengthFieldPrepender {

    public RpcFrameEncoder() {
        super(2);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }
}
