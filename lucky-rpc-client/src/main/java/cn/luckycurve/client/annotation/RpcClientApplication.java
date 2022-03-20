package cn.luckycurve.client.annotation;

import cn.luckycurve.client.ClientApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LuckyCurve
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan(basePackageClasses = {ClientApplication.class})
@ComponentScan
@Component
public @interface RpcClientApplication {
}
