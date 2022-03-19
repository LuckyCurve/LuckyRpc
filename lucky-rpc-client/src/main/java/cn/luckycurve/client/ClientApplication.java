package cn.luckycurve.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author LuckyCurve
 */
@Slf4j
@ComponentScan
@Component
public class ClientApplication {

    @Resource
    Client client;

    @PostConstruct
    public void init() throws Exception {
        client.start();
        log.info("client init success!");
    }

    @PreDestroy
    public void destroy() {
        client.stop();
    }
}
