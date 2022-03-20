package cn.luckycurve.luckyrpctestclient.controller;

import cn.luckycurve.annotation.RpcAutowired;
import cn.luckycurve.test.TimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LuckyCurve
 */
@RestController
public class TimeController {

    @RpcAutowired
    TimeService timeService;

    @GetMapping("/")
    public String now() {
        return timeService.now();
    }
}
