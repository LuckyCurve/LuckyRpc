package cn.luckycurve.luckyrpctestserver.impl;

import cn.luckycurve.annotation.RpcService;
import cn.luckycurve.test.TimeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author LuckyCurve
 */
@RpcService(value = TimeService.class)
public class TimeServiceImpl implements TimeService {
    @Override
    public String now() {
        return DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
    }
}
