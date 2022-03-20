package cn.luckycurve.server.annotation;

import cn.luckycurve.server.ServerApplication;
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
@ComponentScan(basePackageClasses = {ServerApplication.class})
@ComponentScan
@Component
public @interface RpcServerApplication {

}
