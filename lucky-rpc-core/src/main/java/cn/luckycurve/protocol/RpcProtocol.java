package cn.luckycurve.protocol;

import cn.luckycurve.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 存储RPC服务的一些信息
 *
 * @author LuckyCurve
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcProtocol implements Serializable {

    private String ipAddress;

    private Integer port;

    private List<ServiceInfo> serviceInfos;

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public static RpcProtocol getInstance(String json) {
        return JsonUtil.fromJson(json, RpcProtocol.class);
    }
}
