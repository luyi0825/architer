package io.github.architers.cache.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RMap;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * redisson的Api测试
 */
@SpringBootTest
public class RedissionApiTest {
    @Autowired
    private RedissonClient redisson;


    /**
     *
     */
    @Test
    public void testBucket() {
        redisson.getBucket("bucket:test").set("1", 1L, TimeUnit.MINUTES);
    }


    @Test
    public void testMap() throws InterruptedException {
        RMap<String, Object> rMap = redisson.getMap("test");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int j = 0; j < 10; j++)
            new Thread(() -> {
                for (int i = 1; i < 100000L; i++) {
                    rMap.put(UUID.randomUUID().toString(), i);
                }
                countDownLatch.countDown();
            }).start();
        countDownLatch.await();

    }

    @Test
    public void testSet() throws InterruptedException {
        RSet<Object> set = redisson.getSet("set");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int j = 0; j < 10; j++)
            new Thread(() -> {
                for (int i = 1; i < 100000L; i++) {
                    set.add(UUID.randomUUID());
                }
                countDownLatch.countDown();
            }).start();
        countDownLatch.await();
    }
}
