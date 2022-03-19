package cn.luckycurve.util;

import lombok.experimental.UtilityClass;

/**
 * 完成对ResponseBody的类型转换
 *
 * @author LuckyCurve
 */
@UtilityClass
public class CastUtil {

    public <T> T cast(Object src, Class<T> clazz) {
        return clazz.cast(src);
    }
}
