package cn.luckycurve.client.codec;

import cn.luckycurve.common.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * T: Message From where
 *
 * @author LuckyCurve
 */
@Slf4j
public class RpcProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        log.info("execute RpcProtocolDecoder operation");
        final RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.decode(byteBuf);

        out.add(rpcResponse);
    }
}
