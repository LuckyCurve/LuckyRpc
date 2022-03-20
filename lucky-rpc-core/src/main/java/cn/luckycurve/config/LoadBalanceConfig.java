package cn.luckycurve.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @author LuckyCurve
 */
@Slf4j
public class LoadBalanceConfig {

    static {
        final Properties properties = new Properties();
        try {
            properties.load(LoadBalanceConfig.class.getClassLoader().getResourceAsStream("rpc.properties"));
        } catch (Exception e) {
            log.info("load rpc config failed! using default properties");
            e.printStackTrace();
        }

        CPU_RATE = Integer.valueOf(properties.getProperty(LoadBalanceConfigProperties.CPU_RATE, "1"));
        MEMORY_RATE = Integer.valueOf(properties.getProperty(LoadBalanceConfigProperties.MEMORY_RATE, "1"));
    }

    public static final Integer CPU_RATE;
    public static final Integer MEMORY_RATE;


    interface LoadBalanceConfigProperties {
        String CPU_RATE = "rpc.loadBalance.cpuRate";
        String MEMORY_RATE = "rpc.loadBalance.memoryRate";
    }
}
