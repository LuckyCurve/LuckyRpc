package cn.luckycurve.server;

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
@Component
@ComponentScan
public class ServerApplication {

    @Resource
    Server server;

    @PostConstruct
    public void init() {
        server.start();
        log.info("server init success!");
    }

    @PreDestroy
    public void destroy() {
        server.stop();
    }
}
