package cn.luckycurve.client.dispatch;

import cn.luckycurve.common.RpcResponse;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LuckyCurve
 */
@Component
public class ResponseDispatchCenter {

    private Map<Long, ResponseFuture> map = Maps.newConcurrentMap();

    /**
     * 等待添加
     */
    public void add(Long streamId, ResponseFuture future) {
        map.put(streamId, future);
    }

    /**
     * 结果设置
     */
    public void set(Long streamId, RpcResponse result) {
        final ResponseFuture responseFuture = map.get(streamId);

        if (responseFuture != null) {
            responseFuture.setSuccess(result);
            map.remove(streamId);
        }
    }
}
