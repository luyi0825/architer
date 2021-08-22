package com.architecture.test.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheableController {
    @Autowired
    private CacheableUserInfoService cacheableUserInfoService;

    @RequestMapping("/test")
    public void test() {
        cacheableUserInfoService.oneCacheable1("99");
    }
}
