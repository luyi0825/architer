package com.architecture.ultimate.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/test")
    public String test() {
        return port + "";
    }
}
