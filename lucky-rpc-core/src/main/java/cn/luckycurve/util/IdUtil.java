package cn.luckycurve.util;

import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author LuckyCurve
 */
@UtilityClass
public class IdUtil {
    private AtomicLong IDX = new AtomicLong();

    public Long next() {
        return IDX.incrementAndGet();
    }

}
