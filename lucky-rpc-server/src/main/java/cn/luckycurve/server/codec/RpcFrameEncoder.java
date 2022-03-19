package cn.luckycurve.server.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author LuckyCurve
 */
public class RpcFrameEncoder extends LengthFieldPrepender {

    public RpcFrameEncoder() {
        super(2);
    }
}
