package cn.luckycurve.common;

import cn.luckycurve.common.auth.AuthRequestBody;
import cn.luckycurve.common.auth.AuthResponseBody;
import cn.luckycurve.common.common.CommonRequestBody;
import cn.luckycurve.common.common.CommonResponseBody;
import cn.luckycurve.common.keepalive.KeepaliveRequestBody;
import cn.luckycurve.common.keepalive.KeepaliveResponseBody;
import cn.luckycurve.common.loadbalance.LoadBalanceRequestBody;
import cn.luckycurve.common.loadbalance.LoadBalanceResponseBody;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Predicate;

/**
 * @author LuckyCurve
 */
@AllArgsConstructor
@Getter
public enum OperationType {
    /**
     * Common Request And Response
     */
    COMMON(1, CommonRequestBody.class, CommonResponseBody.class),

    /**
     * Auth Request And Response
     */
    AUTH(2, AuthRequestBody.class, AuthResponseBody.class),

    /**
     * Load Balance Request And Response
     */
    LOAD_BALANCE(3, LoadBalanceRequestBody.class, LoadBalanceResponseBody.class),

    /**
     * Keep Alive Request And Response
     */
    KEEP_ALIVE(4, KeepaliveRequestBody.class, KeepaliveResponseBody.class);

    private final Integer opCode;
    private final Class<? extends RequestBody> requestClazz;
    private final Class<? extends ResponseBody> responseClazz;


    public static OperationType getOperationTypeByOpCode(Integer opCode) {
        return getOperationType(operationType -> operationType.getOpCode().equals(opCode));
    }

    public static OperationType getOperationTypeByRequestClass(Class<?> clazz) {
        return getOperationType(operationType -> operationType.getRequestClazz().equals(clazz));
    }

    public static OperationType getOperationTypeByResponseClass(Class<?> clazz) {
        return getOperationType(operationType -> operationType.getResponseClazz().equals(clazz));
    }

    public static Class<?> getResponseClassByOpcode(Integer opCode) {
        return getOperationTypeByOpCode(opCode).getResponseClazz();
    }

    private static OperationType getOperationType(Predicate<OperationType> predicate) {
        final OperationType[] values = values();

        for (OperationType type : values) {
            if (predicate.test(type)) {
                return type;
            }
        }

        throw new AssertionError("no found type!");
    }
}
