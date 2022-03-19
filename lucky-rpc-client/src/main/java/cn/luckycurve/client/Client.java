package cn.luckycurve.client;

import cn.luckycurve.client.manager.ConnectionManager;
import cn.luckycurve.client.proxy.RpcAutowiredAnnotationEnhancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author LuckyCurve
 */
@Slf4j
@Component
public class Client {
    @Resource
    ConnectionManager connectionManager;

    @Resource
    RpcAutowiredAnnotationEnhancer enhancer;

    /**
     * 因为这里线程会阻塞住，线程不会重用，所以无需使用线程池
     */
    public void start() throws Exception {
        enhancer.annotationHandler(connectionManager.initRegistryService());
    }

    public void stop() {
        connectionManager.stop();
    }
}
