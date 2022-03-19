package cn.luckycurve.loadbalance;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * 根据得分进行选择
 *
 * @author LuckyCurve
 */
public class ScoreRpcLoadBalance extends RpcLoadBalance {
    @Override
    public Channel route(Map<InetSocketAddress, Channel> channelMap, Map<InetSocketAddress, Double> scoreMap) {
        InetSocketAddress address = null;
        double maxScore = -1;

        for (Map.Entry<InetSocketAddress, Double> entry : scoreMap.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                address = entry.getKey();
            }
        }

        return channelMap.get(address);
    }
}
