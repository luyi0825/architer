package com.architecture.test.cache;


import com.architecture.context.cache.CacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author luyi
 */
@SpringBootTest
public class CacheableTest {
    @Autowired
    private CacheableService cacheableService;

    @Autowired
    private CacheService cacheService;

    /**
     * 测试一个注解
     */
    @Test
    public void testOneCacheable() {
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < 5; i++) {
            UserInfo userInfo = cacheableService.oneCacheable(userId);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试两个注解
     */
    @Test
    public void testTwoCacheable() {
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < 5; i++) {
            //删除一个，再获取
            cacheService.delete(userId);
            UserInfo userInfo = cacheableService.twoCacheable(userId);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试expireTime
     */
    @Test
    public void testExpireTime() {
        int count = 5;
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = cacheableService.expireTime_1(userId);
            Assertions.assertNotNull(userInfo);
        }

        userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            String finalUserId = userId;
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                UserInfo userInfo = cacheableService.expireTime_2(finalUserId);
                Assertions.assertNull(userInfo);
            });
        }
        userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            UserInfo userInfo = cacheableService.expireTime_3(userId);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试随机时间
     */
    @Test
    public void testRandomTime() {
        int count = 100;
        for (int i = 0; i < count; i++) {
            String userName = UUID.randomUUID().toString();
            cacheableService.randomTime(userName);
            UserInfo userInfo = cacheableService.randomTime(userName);
            Assertions.assertNotNull(userInfo);
        }
    }

    /**
     * 测试条件
     */
    @Test
    public void testCondition() {
        //缓存，查询1次
        String userName = "1".repeat(11);
        cacheableService.condition(userName);
        UserInfo userInfo = cacheableService.condition(userName);
        Assertions.assertNotNull(userInfo);
        //不缓存：查询db两次
        userName = "2".repeat(10);
        cacheableService.condition(userName);
        userInfo = cacheableService.condition(userName);
        Assertions.assertNotNull(userInfo);
    }

}
