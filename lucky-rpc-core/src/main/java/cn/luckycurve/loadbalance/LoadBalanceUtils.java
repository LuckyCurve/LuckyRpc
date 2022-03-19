package cn.luckycurve.loadbalance;

import cn.hutool.core.util.NumberUtil;
import cn.luckycurve.metric.HardwareInfo;

import static cn.luckycurve.config.LoadBalanceConfig.CPU_RATE;
import static cn.luckycurve.config.LoadBalanceConfig.MEMORY_RATE;

/**
 * @author LuckyCurve
 */
public class LoadBalanceUtils {

    /**
     * @return 当前负载均衡得分
     */
    public static Double score(HardwareInfo cur) {

        Double cpuPower = (100 - cur.getCpuUsed()) * cur.getCpuNum() * CPU_RATE;
        Double memoryPower = (100 - cur.getMemoryUsed()) * cur.getMemoryTotal() * MEMORY_RATE;

        return NumberUtil.round(cpuPower + memoryPower, 2).doubleValue();
    }
}