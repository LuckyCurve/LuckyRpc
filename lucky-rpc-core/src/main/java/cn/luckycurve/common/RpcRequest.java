package cn.luckycurve.common;

/**
 * @author LuckyCurve
 */
public class RpcRequest extends Message<MessageBody> {

    public RpcRequest() {
    }

    public RpcRequest(Long streamId, RequestBody body) {
        setHeader(MessageHeader.builder()
                .opCode(OperationType.getOperationTypeByRequestClass(body.getClass()).getOpCode())
                .streamId(streamId)
                .build());
        setBody(body);
    }

    @Override
    public Class getClassByOpCode(Integer opCode) {
        return OperationType.getOperationTypeByOpCode(opCode).getRequestClazz();
    }
}
