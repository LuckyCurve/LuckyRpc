package cn.luckycurve.client.proxy;

import cn.luckycurve.annotation.RpcAutowired;
import cn.luckycurve.client.dispatch.ResponseDispatchCenter;
import cn.luckycurve.client.dispatch.ResponseFuture;
import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.common.CommonRequestBody;
import cn.luckycurve.common.common.CommonResponseBody;
import cn.luckycurve.util.IdUtil;
import io.netty.channel.Channel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;

/**
 * 完成对RpcAutowired注解的增强
 *
 * @author LuckyCurve
 */
@Component
public class RpcAutowiredAnnotationEnhancer {

    @Resource
    ApplicationContext context;

    @Resource
    ResponseDispatchCenter center;

    public void annotationHandler(Channel channel) throws IllegalAccessException {
        final String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            if ("clientApplication".equals(name)) {
                continue;
            }
            final Object bean = context.getBean(name);
            for (Field field : bean.getClass().getDeclaredFields()) {
                final RpcAutowired annotation = field.getAnnotation(RpcAutowired.class);
                if (annotation != null) {
                    doAnnotationHandler(field, bean, annotation.version(), channel);
                }
            }
        }
    }

    private void doAnnotationHandler(Field field, Object bean, String version, Channel channel) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(bean, Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{field.getType()}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        final Method[] methods = proxy.getClass().getDeclaredMethods();
                        if (Arrays.stream(methods).map(Method::getName).anyMatch(name -> Objects.equals(method.getName(), name))) {
                            final RpcRequest request = new RpcRequest(IdUtil.next(),
                                    new CommonRequestBody(version, method.getDeclaringClass().getTypeName(), method.getName(), args));

                            final ResponseFuture future = new ResponseFuture();
                            center.add(request.getHeader().getStreamId(), future);

                            channel.writeAndFlush(request);

                            return ((CommonResponseBody) future.get().getBody()).getResult();
                        }
                        throw new UnsupportedOperationException("unsupportedOperationException method: " + method.getName());
                    }
                }));
    }
}
