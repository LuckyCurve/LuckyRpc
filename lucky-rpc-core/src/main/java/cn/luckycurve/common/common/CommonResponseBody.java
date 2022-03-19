package cn.luckycurve.common.common;

import cn.luckycurve.common.ResponseBody;
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
public class CommonResponseBody extends ResponseBody {

    private Integer code;

    private String error;

    private Object result;
}
