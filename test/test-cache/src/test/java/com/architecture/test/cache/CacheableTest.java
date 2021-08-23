package com.architecture.test.cache;


import com.architecture.context.cache.CacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * 测试unless
     */
    @Test
    public void testUnless() {
        //缓存，查询1次
        String userName = "no_unless";
        cacheableService.unless(userName);
        UserInfo userInfo = cacheableService.unless(userName);
        Assertions.assertNotNull(userInfo);
        //不缓存：查询db两次
        userName = "unless";
        cacheableService.unless(userName);
        userInfo = cacheableService.unless(userName);
        Assertions.assertNotNull(userInfo);
    }

    /**
     * 测试并发
     */
    @Test
    public void testToGather() throws InterruptedException {
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                cacheableService.toGather(userName);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
    }

    /**
     * 测试并发
     */
    @Test
    public void testLockToGather() throws InterruptedException {
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    cacheableService.testLockToGather(userName);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }


            });
        }
        countDownLatch.await();

    }


}
