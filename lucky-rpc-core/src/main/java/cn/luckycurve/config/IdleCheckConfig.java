package cn.luckycurve.config;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @author LuckyCurve
 */
@Slf4j
public class IdleCheckConfig {

    static {
        final Properties properties = new Properties();
        try {
            properties.load(LoadBalanceConfig.class.getClassLoader().getResourceAsStream("rpc.properties"));
        } catch (Exception e) {
            log.info("load rpc config failed! using default properties");
            e.printStackTrace();
        }

        HEART_TIME = Integer.valueOf(properties.getProperty(IdleCheckConfigProperties.HEART_TIME, "5"));
    }

    public static final Integer HEART_TIME;


    interface IdleCheckConfigProperties {
        String HEART_TIME = "rpc.idleCheck.heartTime";
    }
}
