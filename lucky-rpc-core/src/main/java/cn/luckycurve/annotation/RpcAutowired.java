package cn.luckycurve.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 完成服务发现
 *
 * @author LuckyCurve
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Component
// fixme: 在SpringBootTest当中入法注入对象
public @interface RpcAutowired {
    /**
     * 注册的版本号
     */
    String version() default "";
}
