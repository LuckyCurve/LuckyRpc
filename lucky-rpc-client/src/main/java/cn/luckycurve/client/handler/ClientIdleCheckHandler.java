package cn.luckycurve.client.handler;

import cn.luckycurve.config.IdleCheckConfig;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author LuckyCurve
 */
public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler() {
        super(0, IdleCheckConfig.HEART_TIME, 0);
    }
}
