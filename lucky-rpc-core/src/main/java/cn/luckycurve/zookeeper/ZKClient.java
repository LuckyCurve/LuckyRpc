package cn.luckycurve.zookeeper;

import cn.luckycurve.config.ZookeeperConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * zookeeper 客户端
 *
 * @author LuckyCurve
 */
public class ZKClient {

    private static ZKClient INSTANCE = new ZKClient(ZookeeperConfig.ADDRESS);

    public static ZKClient getInstance() {
        return INSTANCE;
    }

    private CuratorFramework client;

    private ZKClient() {
    }

    private ZKClient(InetSocketAddress registerAddress) {
        client = CuratorFrameworkFactory.builder()
                .namespace(ZookeeperConfig.ZK_NAMESPACE)
                .connectionTimeoutMs(ZookeeperConfig.ZK_CONNECTION_TIMEOUT)
                .sessionTimeoutMs(ZookeeperConfig.ZK_SESSION_TIMEOUT)
                // 重试策略
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .connectString(registerAddress.getHostString() + ":" + registerAddress.getPort())
                .build();

        client.start();
    }

    public String createPathData(String path, byte[] data) throws Exception {
        return client.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path, data);
    }

    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    public byte[] getData(String path) throws Exception {
        return client.getData().forPath(path);
    }

    public void deletePath(String path) throws Exception {
        client.delete().deletingChildrenIfNeeded().forPath(path);
    }

    public void addConnectionStateListener(ConnectionStateListener listener) {
        client.getConnectionStateListenable().addListener(listener);
    }

    public void close() {
        client.close();
    }
}
