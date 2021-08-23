package com.architecture.test.cache.put;

import com.architecture.test.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试putCache注解
 */
public class PutTest {
    @Autowired
    private PutService putService;

    @Test
    public void testOnePut() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        putService.onePut(userInfo);
    }

}
