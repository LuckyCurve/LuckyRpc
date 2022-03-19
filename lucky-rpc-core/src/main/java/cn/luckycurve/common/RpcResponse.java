package cn.luckycurve.common;

/**
 * @author LuckyCurve
 */
public class RpcResponse extends Message<MessageBody> {
    @Override
    public Class getClassByOpCode(Integer opCode) {
        return OperationType.getOperationTypeByOpCode(opCode)
                .getResponseClazz();
    }
}
