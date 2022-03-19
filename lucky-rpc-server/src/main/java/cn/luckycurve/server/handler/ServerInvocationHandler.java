package cn.luckycurve.server.handler;

import cn.luckycurve.annotation.RpcService;
import cn.luckycurve.common.MessageHeader;
import cn.luckycurve.common.OperationType;
import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.RpcResponse;
import cn.luckycurve.common.common.CommonRequestBody;
import cn.luckycurve.common.common.CommonResponseBody;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 完成对服务端的调用
 *
 * @author LuckyCurve
 */
@Slf4j
@AllArgsConstructor
public class ServerInvocationHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final ApplicationContext context;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) {
        final RpcResponse response = new RpcResponse();
        response.setHeader(MessageHeader.builder().streamId(request.getHeader().getStreamId())
                .opCode(OperationType.COMMON.getOpCode()).build());

        try {
            final Object result = handle(request);
            response.setBody(CommonResponseBody.builder().result(result).build());
        } catch (Throwable throwable) {
            log.error("when handle service streamId:{} throw error: {}", request.getHeader().getStreamId(), throwable);
            response.setBody(CommonResponseBody.builder().error(throwable.toString()).build());
        }

        ctx.writeAndFlush(response).addListener(future -> {
            log.info("send response for request id: {}", request.getHeader().getStreamId());
        });
    }

    /**
     * 完成对请求的处理
     */
    private Object handle(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (!rpcRequest.getHeader().getOpCode().equals(OperationType.COMMON.getOpCode())) {
            throw new AssertionError("in server handler occur other opcode!");
        }

        final CommonRequestBody commonRequestBody = (CommonRequestBody) rpcRequest.getBody();

        final Map<String, ?> beansMap = context.getBeansOfType(Class.forName(commonRequestBody.getInterfaceName()));

        // 完成对版本号的筛选
        final List<?> list = beansMap.entrySet().stream().map(Map.Entry::getValue).filter(bean ->
                bean.getClass().getAnnotation(RpcService.class).version().equals(commonRequestBody.getVersion()))
                .collect(Collectors.toList());

        if (list.size() == 0) {
            throw new IllegalArgumentException("no server bean found in server!");
        }

        if (list.size() > 1) {
            throw new IllegalArgumentException("multi current version Server found in server!");
        }

        final Object handler = list.get(0);
        Class<?> clazz = handler.getClass();

        Class<?>[] parametersType = null;

        if (commonRequestBody.getParameters() != null) {
            parametersType = new Class[commonRequestBody.getParameters().length];

            for (int i = 0; i < commonRequestBody.getParameters().length; i++) {
                parametersType[i] = commonRequestBody.getParameters()[i].getClass();
            }
        }

        final Method method = clazz.getMethod(commonRequestBody.getMethodName(), parametersType);

        return method.invoke(handler, commonRequestBody.getParameters());
    }
}
