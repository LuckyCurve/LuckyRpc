package cn.luckycurve.common;

import cn.luckycurve.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import lombok.Data;

/**
 * *******************************************************
 * Length        opCode      streamId
 * body
 *
 * @author LuckyCurve
 */
@Data
public abstract class Message<T extends MessageBody> {

    private MessageHeader header;
    private T body;

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(header.getOpCode());
        byteBuf.writeLong(header.getStreamId());

        byteBuf.writeBytes(JsonUtil.toJson(body).getBytes());
    }

    public abstract Class<T> getClassByOpCode(Integer opCode);

    public void decode(ByteBuf byteBuf) {
        this.header = new MessageHeader(byteBuf.readInt(), byteBuf.readLong());

        final Class<T> clazz = getClassByOpCode(header.getOpCode());

        this.body = JsonUtil.fromJson(byteBuf.toString(CharsetUtil.UTF_8), clazz);
    }
}
