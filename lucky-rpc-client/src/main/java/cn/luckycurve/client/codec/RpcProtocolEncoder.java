package cn.luckycurve.client.codec;

import cn.luckycurve.common.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * T: Input Message
 *
 * @author LuckyCurve
 */
public class RpcProtocolEncoder extends MessageToMessageEncoder<RpcRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequest rpcRequest, List<Object> out) throws Exception {
        final ByteBuf buffer = ctx.alloc().buffer();
        rpcRequest.encode(buffer);

        out.add(buffer);
    }
}
