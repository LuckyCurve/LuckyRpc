package cn.luckycurve.common.loadbalance;

import cn.luckycurve.common.RequestBody;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 做负载均衡的RequestBody
 *
 * @author LuckyCurve
 */
@Data
@Builder
@NoArgsConstructor
public class LoadBalanceRequestBody extends RequestBody {
}
