package cn.luckycurve.common.common;

import cn.luckycurve.common.RequestBody;
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
public class CommonRequestBody extends RequestBody {
    @Builder.Default
    private String version = "1.0";

    private String interfaceName;

    private String methodName;

    private Object[] parameters;
}
