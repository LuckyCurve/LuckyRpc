package cn.luckycurve.common.auth;

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
public class AuthRequestBody extends RequestBody {
    private String username;
    private String password;
}
