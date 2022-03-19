package cn.luckycurve.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 完成服务注册，标注在需要发布的服务类上
 *
 * @author LuckyCurve
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {
    /**
     * 发布的服务类型
     */
    Class<?> value();

    /**
     * 发布版本号
     */
    String version() default "";
}
