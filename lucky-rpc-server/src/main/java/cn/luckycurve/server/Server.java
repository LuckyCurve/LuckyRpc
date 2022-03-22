package cn.luckycurve.server;

import cn.luckycurve.annotation.RpcService;
import cn.luckycurve.config.ZookeeperConfig;
import cn.luckycurve.protocol.RpcProtocol;
import cn.luckycurve.protocol.ServiceInfo;
import cn.luckycurve.server.codec.RpcFrameDecoder;
import cn.luckycurve.server.codec.RpcFrameEncoder;
import cn.luckycurve.server.codec.RpcProtocolDecoder;
import cn.luckycurve.server.codec.RpcProtocolEncoder;
import cn.luckycurve.server.config.ServerConfig;
import cn.luckycurve.server.handler.*;
import cn.luckycurve.util.IpUtil;
import cn.luckycurve.zookeeper.ZKClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LuckyCurve
 */
@Component
@Slf4j
public class Server {

    @Resource
    ApplicationContext context;

    ZKClient zkClient = ZKClient.getInstance();

    /**
     * 实际上Server端只会绑定到Boss Group当中的一个去，因此可以直接设置参数为1
     */
    final NioEventLoopGroup boss = new NioEventLoopGroup(1);
    final NioEventLoopGroup worker = new NioEventLoopGroup();

    /**
     * todo: 压力测试，性能较之于不用的提升
     */
    final UnorderedThreadPoolEventExecutor businessExecutor = new UnorderedThreadPoolEventExecutor(16, new DefaultThreadFactory("businessExecutor"));

    /**
     * 新建线程以异步方式启动Server，线程无法重用，无需使用线程池
     */
    public void start() {
        new Thread(() -> {
            try {
                final ServerBootstrap serverBootstrap = new ServerBootstrap()
                        .group(boss, worker)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel ch) {
                                ch.pipeline()
                                        .addLast(new LoggingHandler(LogLevel.INFO))

                                        .addLast(new ServerIdleCheckHandler())

                                        .addLast(new RpcFrameDecoder())
                                        .addLast(new RpcFrameEncoder())

                                        .addLast(new RpcProtocolDecoder())
                                        .addLast(new RpcProtocolEncoder())

                                        .addLast(businessExecutor, new ComputerStatusHandler())
                                        .addLast(businessExecutor, new AuthServerHandler())
                                        .addLast(businessExecutor, new ServerKeepaliveHandler())
                                        .addLast(businessExecutor, new ServerInvocationHandler(context));
                            }
                        });

                final ChannelFuture future = serverBootstrap.bind(ServerConfig.PORT).sync();

                // after server init
                registryService();

                future.channel().closeFuture().sync();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                log.error("Server Init Failed!");
            } finally {
                stop();
            }
        }, "main-ServerInitThread").start();
    }

    public void stop() {
        zkClient.close();
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }

    private void registryService() throws Exception {
        final String[] names = context.getBeanDefinitionNames();
        List<ServiceInfo> serviceInfos = new ArrayList<>();

        for (String name : names) {
            final Class<?> clazz = context.getBean(name).getClass();
            final RpcService rpcServiceAnnotation = clazz.getAnnotation(RpcService.class);


            if (rpcServiceAnnotation != null) {
                // box in class ServiceInfo
                final ServiceInfo info = new ServiceInfo(rpcServiceAnnotation.version(), rpcServiceAnnotation.value().getTypeName(),
                        Arrays.stream(clazz.getDeclaredMethods()).map(Method::toString).collect(Collectors.toList()));
                serviceInfos.add(info);
            }
        }

        final RpcProtocol rpcProtocol = new RpcProtocol(IpUtil.getLocalHostLANAddress().getHostAddress(), ServerConfig.PORT, serviceInfos);

        // finish zookeeper registry
        zkClient.createPathData(ZookeeperConfig.ZK_DATA_PATH, rpcProtocol.toJson().getBytes());
    }
}