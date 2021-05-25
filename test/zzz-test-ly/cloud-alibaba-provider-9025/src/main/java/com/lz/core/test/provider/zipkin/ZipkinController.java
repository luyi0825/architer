package com.lz.core.test.provider.zipkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 * 测试zipkin的controller
 */
@RestController
@RequestMapping("/zipkin")
public class ZipkinController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/get/{id}")
    public String getXxx(@PathVariable(name = "id") String id) {
        if ("1".equals(id)) {
            throw new RuntimeException("嗯哼1");
        } else if ("2".equals(id)) {
            throw new IllegalArgumentException("嗯哼2");
        }
        return "xxx_" + id + ":" + port;
    }

}
