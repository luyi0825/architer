package io.github.architers.test;

import io.github.architers.test.feign.NextServiceFeign;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luyi
 * 测试工具类
 */
@RestController
@RequestMapping("/up")
public class UpController {

    @Resource
    private NextServiceFeign nextServiceFeign;

    @GetMapping("/test")
    public String test() {
        return nextServiceFeign.test();
    }

    public static synchronized void main(String[] args) {

    }

}
