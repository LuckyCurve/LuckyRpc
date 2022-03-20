package cn.luckycurve.config;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * @author LuckyCurve
 */
@Slf4j
public class ZookeeperConfig {
    static {
        final Properties properties = new Properties();
        try {
            properties.load(ZookeeperConfig.class.getClassLoader().getResourceAsStream("rpc.properties"));
        } catch (Exception e) {
            log.info("load rpc config failed! using default properties");
            e.printStackTrace();
        }

        String host = properties.getProperty(ZookeeperConfigProperties.HOST, "localhost");
        Integer port = Integer.valueOf(properties.getProperty(ZookeeperConfigProperties.PORT, "2181"));
        ADDRESS = new InetSocketAddress(host, port);

        ZK_SESSION_TIMEOUT = Integer.valueOf(properties.getProperty(ZookeeperConfigProperties.ZK_SESSION_TIMEOUT, "5000"));
        ZK_CONNECTION_TIMEOUT = Integer.valueOf(properties.getProperty(ZookeeperConfigProperties.ZK_CONNECTION_TIMEOUT, "5000"));

        ZK_REGISTRY_PATH = properties.getProperty(ZookeeperConfigProperties.ZK_REGISTRY_PATH, "/registry");
        ZK_DATA_PATH = properties.getProperty(ZookeeperConfigProperties.ZK_REGISTRY_PATH, "/registry") + properties.getProperty(ZookeeperConfigProperties.ZK_DATA_PATH, "/data");
        ZK_NAMESPACE = properties.getProperty(ZookeeperConfigProperties.ZK_NAMESPACE, "netty-rpc");
    }


    public static final InetSocketAddress ADDRESS;

    public static final Integer ZK_SESSION_TIMEOUT;
    public static final Integer ZK_CONNECTION_TIMEOUT;

    public static final String ZK_REGISTRY_PATH;
    public static final String ZK_DATA_PATH;

    public static final String ZK_NAMESPACE;

    interface ZookeeperConfigProperties {
        String HOST = "rpc.zookeeper.host";
        String PORT = "rpc.zookeeper.port";

        String ZK_SESSION_TIMEOUT = "rpc.zookeeper.ZkSessionTimeout";
        String ZK_CONNECTION_TIMEOUT = "rpc.zookeeper.ZkConnectionTimeout";

        String ZK_REGISTRY_PATH = "rpc.zookeeper.registryPath";
        String ZK_DATA_PATH = "rpc.zookeeper.dataPath";

        String ZK_NAMESPACE = "rpc.zookeeper.namespace";
    }
}
