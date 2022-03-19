package cn.luckycurve.common.auth;

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
public class AuthResponseBody extends ResponseBody {
    private Boolean pass;
}
