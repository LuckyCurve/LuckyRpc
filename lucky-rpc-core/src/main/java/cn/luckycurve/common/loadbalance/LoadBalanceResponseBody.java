package cn.luckycurve.common.loadbalance;

import cn.luckycurve.common.ResponseBody;
import cn.luckycurve.metric.HardwareInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LuckyCurve
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadBalanceResponseBody extends ResponseBody {

    private HardwareInfo info;

    private Double score;
}
