package cn.luckycurve.client.handler;

import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author LuckyCurve
 */
public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler() {
        super(0, 5, 0);
    }
}
