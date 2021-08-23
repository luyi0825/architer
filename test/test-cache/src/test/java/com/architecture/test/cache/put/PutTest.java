package com.architecture.test.cache.put;

import com.architecture.test.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试putCache注解
 */
@SpringBootTest
public class PutTest {
    @Autowired
    private PutService putService;

    /**
     * 测试一个注解
     */
    @Test
    public void testOnePut() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        putService.onePut(userInfo);
    }

}
