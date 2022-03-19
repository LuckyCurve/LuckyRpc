package cn.luckycurve.server.codec;


import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author LuckyCurve
 */
public class RpcFrameDecoder extends LengthFieldBasedFrameDecoder {

    public RpcFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
