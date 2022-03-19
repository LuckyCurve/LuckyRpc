package cn.luckycurve.server.handler;


import cn.luckycurve.common.MessageHeader;
import cn.luckycurve.common.OperationType;
import cn.luckycurve.common.RpcRequest;
import cn.luckycurve.common.RpcResponse;
import cn.luckycurve.common.loadbalance.LoadBalanceRequestBody;
import cn.luckycurve.common.loadbalance.LoadBalanceResponseBody;
import cn.luckycurve.loadbalance.LoadBalanceUtils;
import cn.luckycurve.metric.HardwareInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 返回得分用于负载均衡
 *
 * @author LuckyCurve
 */
public class ComputerStatusHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RpcRequest) {
            final RpcRequest request = (RpcRequest) msg;

            if (request.getBody() instanceof LoadBalanceRequestBody) {
                final RpcResponse response = new RpcResponse();
                response.setHeader(MessageHeader.builder().streamId(request.getHeader().getStreamId())
                        .opCode(OperationType.LOAD_BALANCE.getOpCode()).build());

                final HardwareInfo hardwareInfo = HardwareInfo.cur();

                response.setBody(LoadBalanceResponseBody.builder()
                        .info(hardwareInfo)
                        .score(LoadBalanceUtils.score(hardwareInfo))
                        .build());

                ctx.writeAndFlush(response);
            } else {
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
