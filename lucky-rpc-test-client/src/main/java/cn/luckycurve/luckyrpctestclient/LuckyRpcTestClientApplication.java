package cn.luckycurve.luckyrpctestclient;

import cn.luckycurve.client.annotation.RpcClientApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RpcClientApplication
public class LuckyRpcTestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuckyRpcTestClientApplication.class, args);
    }

}
