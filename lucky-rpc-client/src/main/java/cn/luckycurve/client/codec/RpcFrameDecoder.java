package cn.luckycurve.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LuckyCurve
 */
@Slf4j
public class RpcFrameDecoder extends LengthFieldBasedFrameDecoder {

    public RpcFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
