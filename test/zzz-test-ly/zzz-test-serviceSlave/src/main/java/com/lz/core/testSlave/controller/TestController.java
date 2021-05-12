package com.lz.core.testSlave.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/get")
    public String get() {
        return "1";
    }
}
