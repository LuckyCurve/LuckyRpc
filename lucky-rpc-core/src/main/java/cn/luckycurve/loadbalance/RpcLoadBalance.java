package cn.luckycurve.loadbalance;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author LuckyCurve
 */
public abstract class RpcLoadBalance {

    /**
     * 根据路由选择最佳Channel
     */
    public abstract Channel route(Map<InetSocketAddress, Channel> channelMap, Map<InetSocketAddress, Double> scoreMap);
}
