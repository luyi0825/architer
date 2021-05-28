package com.lz.core.test.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 * sentinel 对应的controller
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelController {
    @SentinelResource(value = "sayHello")
    @RequestMapping("/test")
    public String test() {
        System.out.println("in test");
        return "test测试";
    }
}
