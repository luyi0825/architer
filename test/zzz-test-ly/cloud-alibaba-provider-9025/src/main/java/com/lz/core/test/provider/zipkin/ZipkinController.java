package com.lz.core.test.provider.zipkin;

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

    @RequestMapping("/get/{id}")
    public String getXxx(@PathVariable(name = "id") String id) {
        return "xxx_" + id;
    }

}
