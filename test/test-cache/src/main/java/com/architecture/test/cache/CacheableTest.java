package com.architecture.test.cache;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author luyi
 */
//@SpringBootTest
public class CacheableTest {
    @Autowired
    private CacheableUserInfoService cacheableUserInfoService;


    @Test
    public void testOneCacheable() {
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = cacheableUserInfoService.oneCacheable1(userId);
            System.out.println(userInfo);
        }
    }

}
