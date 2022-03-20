package cn.luckycurve.luckyrpctestserver;

import cn.luckycurve.server.annotation.RpcServerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RpcServerApplication
public class LuckyRpcTestServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyRpcTestServerApplication.class, args);
    }
}
