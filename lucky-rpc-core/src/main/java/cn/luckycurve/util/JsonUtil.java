package cn.luckycurve.util;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

/**
 * @author LuckyCurve
 */
@UtilityClass
public class JsonUtil {

    private final Gson GSON = new Gson();

    public String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
