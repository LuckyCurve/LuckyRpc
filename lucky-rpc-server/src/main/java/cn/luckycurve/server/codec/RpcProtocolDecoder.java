package cn.luckycurve.server.codec;

import cn.luckycurve.common.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * T: Message From where
 *
 * @author LuckyCurve
 */
public class RpcProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        final RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.decode(byteBuf);

        out.add(rpcRequest);
    }
}
