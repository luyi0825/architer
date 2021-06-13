package com.core.test.provider.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/userConfig")
@RefreshScope
public class UserConfigController {
    @Autowired
    private UserConfig userConfig;

    @Value("${test}")
    private String test;

    @RequestMapping("/get")
    public UserConfig get(){
        return userConfig;
    }

    @RequestMapping("/getTest")
    public String getTest(){
        return test;
    }
}
