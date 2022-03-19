package cn.luckycurve.client.manager;

import cn.luckycurve.client.codec.RpcFrameDecoder;
import cn.luckycurve.client.codec.RpcFrameEncoder;
import cn.luckycurve.client.codec.RpcProtocolDecoder;
import cn.luckycurve.client.codec.RpcProtocolEncoder;
import cn.luckycurve.client.dispatch.ResponseDispatchCenter;
import cn.luckycurve.client.dispatch.ResponseDispatchHandler;
import cn.luckycurve.client.dispatch.ResponseFuture;
import cn.luckycurve.client.handler.ClientIdleCheckHandler;
import cn.luckycurve.client.handler.ClientKeepaliveHandler;
import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.RpcResponse;
import cn.luckycurve.common.auth.AuthRequestBody;
import cn.luckycurve.common.loadbalance.LoadBalanceRequestBody;
import cn.luckycurve.common.loadbalance.LoadBalanceResponseBody;
import cn.luckycurve.config.ZookeeperConfig;
import cn.luckycurve.loadbalance.ScoreRpcLoadBalance;
import cn.luckycurve.protocol.RpcProtocol;
import cn.luckycurve.util.IdUtil;
import cn.luckycurve.zookeeper.ZKClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author LuckyCurve
 */
@Component
@Slf4j
public class ConnectionManager {

    @Resource
    ResponseDispatchCenter center;

    private final Map<InetSocketAddress, Channel> channelMap = new ConcurrentHashMap<>();

    private final Map<InetSocketAddress, Double> scoreMap = new ConcurrentHashMap<>();

    private final ExecutorService executor = new ThreadPoolExecutor(8,
            8, 100, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100), new DefaultThreadFactory("client-discovery"));

    private final EventLoopGroup worker = new NioEventLoopGroup(0, new DefaultThreadFactory("Worker EventLoopGroup"));

    private CountDownLatch latch;

    /**
     * 完成对服务节点的更新并选择最优节点
     */
    public Channel initRegistryService() throws Exception {
        final ZKClient zkClient = ZKClient.getInstance();

        final List<String> paths = zkClient.getChildren(ZookeeperConfig.ZK_REGISTRY_PATH).stream()
                .map(s -> ZookeeperConfig.ZK_REGISTRY_PATH + "/" + s).collect(Collectors.toList());

        List<RpcProtocol> rpcProtocols = new ArrayList<>();

        for (String path : paths) {
            rpcProtocols.add(RpcProtocol.getInstance(new String(zkClient.getData(path))));
        }

        if (rpcProtocols.size() == 0) {
            throw new RuntimeException("can not find server in zookeeper");
        }


        latch = new CountDownLatch(rpcProtocols.size());


        for (RpcProtocol rpcProtocol : rpcProtocols) {

            executor.submit(() -> {
                final InetSocketAddress inetSocketAddress = new InetSocketAddress(rpcProtocol.getIpAddress(), rpcProtocol.getPort());

                final Bootstrap bootstrap = new Bootstrap().group(worker)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) {
                                ch.pipeline()
                                        .addLast(new LoggingHandler(LogLevel.INFO))

                                        .addLast(new ClientIdleCheckHandler())

                                        .addLast(new RpcFrameDecoder())
                                        .addLast(new RpcFrameEncoder())

                                        .addLast(new RpcProtocolDecoder())
                                        .addLast(new RpcProtocolEncoder())

                                        .addLast(new ResponseDispatchHandler(center))

                                        .addLast(new ClientKeepaliveHandler());
                            }
                        });

                try {
                    final ChannelFuture channelFuture = bootstrap.connect(inetSocketAddress).sync();

                    // get server score
                    final Long streamId = IdUtil.next();
                    final RpcRequest request = new RpcRequest(streamId, new LoadBalanceRequestBody());
                    final ResponseFuture future = new ResponseFuture();
                    final Channel channel = channelFuture.channel();
                    center.add(streamId, future);
                    channel.writeAndFlush(request);

                    final RpcResponse response = future.get();
                    final Double score = ((LoadBalanceResponseBody) response.getBody()).getScore();

                    channelMap.put(inetSocketAddress, channel);
                    scoreMap.put(inetSocketAddress, score);

                    log.info("found service host: {}, port: {}", rpcProtocol.getIpAddress(), rpcProtocol.getPort());

                    // 进行权鉴
                    final RpcRequest authRequest = new RpcRequest(IdUtil.next(), new AuthRequestBody("Lucky", "Curve"));
                    channel.writeAndFlush(authRequest);

                    latch.countDown();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        }

        latch.await();

        // 使用IoC方式实现
        final ScoreRpcLoadBalance balance = new ScoreRpcLoadBalance();

        return balance.route(channelMap, scoreMap);
    }

    /**
     * 完成对资源的释放
     */
    public void stop() {
        worker.shutdownGracefully();
        executor.shutdown();
    }

}
