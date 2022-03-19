package cn.luckycurve.server.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @author LuckyCurve
 */
@Slf4j
public class ServerConfig {

    static {
        final Properties properties = new Properties();
        try {
            properties.load(ServerConfig.class.getClassLoader().getResourceAsStream("rpc.properties"));
        } catch (IOException e) {
            log.info("load rpc config failed! using default properties");
            e.printStackTrace();
        }

        PORT = Integer.valueOf(properties.getProperty(ServerConfigProperties.PORT, "8090"));
        USERNAME = properties.getProperty(ServerConfigProperties.USERNAME, "Lucky");
        PASSWORD = properties.getProperty(ServerConfigProperties.PASSWORD, "Curve");
    }

    public static final Integer PORT;
    public static final String USERNAME;
    public static final String PASSWORD;

    interface ServerConfigProperties {
        String PORT = "rpc.server.port";

        String USERNAME = "rpc.server.auth.username";
        String PASSWORD = "rpc.server.auth.password";
    }
}
