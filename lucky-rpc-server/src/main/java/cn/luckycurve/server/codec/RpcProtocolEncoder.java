package cn.luckycurve.server.codec;

import cn.luckycurve.common.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * T: Message From where
 *
 * @author LuckyCurve
 */
public class RpcProtocolEncoder extends MessageToMessageEncoder<RpcResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse rpcResponse, List<Object> out) throws Exception {
        final ByteBuf buffer = ctx.alloc().buffer();
        rpcResponse.encode(buffer);

        out.add(buffer);
    }
}
