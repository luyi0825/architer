package io.github.architers.redisson.cache.cacheable.value;



import io.github.architers.redisson.cache.CacheUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luyi
 * 用于测试@Cacheable注解
 * @version 1.0.0
 */
@SpringBootTest
public class CacheableTest {
    @Autowired
    private CacheableService cacheableService;


//    @Autowired
//    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedissonClient client;

    /**
     * 测试一个@Cacheable注解
     */
    @Test
    public void testOneCacheable() {
        String userName = "123456789";
        long count = 10000;
        //使用注解查询一万次
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            CacheUser cacheUser = cacheableService.oneCacheable(userName);
            Assertions.assertNotNull(cacheUser);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("cacheable耗时：" + (time2 - time1));
        for (int i = 0; i < count; i++) {
            client.getBucket("cacheableService_oneCacheable:" + userName).get();
        }
        long time3 = System.currentTimeMillis();
        System.out.println("使用redissonClient耗时:" + (time3 - time2));
        //用redisTemplate查询10000次
//        for (int i = 0; i < count; i++) {
//            redisTemplate.opsForValue().get("cacheableService_oneCacheable:" + userName);
//        }
//        long time4 = System.currentTimeMillis();
//        System.out.println("使用redissonClient的redisTemplate耗时" + (time4 - time3));

    }

    /**
     * 测试两个注解
     * <li>需要测试是否造成多次查询</li>
     */
    @Test
    public void testTwoCacheable() {
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < 5; i++) {
            //删除一个，再获取
            //  cacheOperate.getSimpleCache("cacheableService_twoCacheable_key2").delete(userId);
            CacheUser CacheUser = cacheableService.twoCacheable(userId);
            Assertions.assertNotNull(CacheUser);
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
            //不过期
            CacheUser CacheUser = cacheableService.expireTime_never(userId);
            Assertions.assertNotNull(CacheUser);
        }

        //3分钟过期
        userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            CacheUser CacheUser = cacheableService.expireTime_3_minutes(userId);
            Assertions.assertNotNull(CacheUser);
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
            CacheUser CacheUser = cacheableService.randomTime(userName);
            Assertions.assertNotNull(CacheUser);
        }
    }

    /**
     * 测试条件
     */
    @Test
    public void testCondition() {
        //缓存，查询1次
        StringBuilder stringBuilder = new StringBuilder("");
        int count = 11;
        for (int i = 0; i < count; i++) {
            stringBuilder.append("1");
        }
        String userName = stringBuilder.toString();
        //长度大于10，查询一次数据库
        cacheableService.condition(userName);
        CacheUser CacheUser = cacheableService.condition(userName);
        Assertions.assertNotNull(CacheUser);
        //长度小于10，查询，不缓存：查询db两次
        userName = stringBuilder.substring(0, count - 2);
        cacheableService.condition(userName);
        CacheUser = cacheableService.condition(userName);
        Assertions.assertNotNull(CacheUser);
    }

    /**
     * 测试unless
     */
    @Test
    public void testUnless() {
        //缓存，查询1次
        String userName = "no_unless";
        cacheableService.unless(userName);
        CacheUser CacheUser = cacheableService.unless(userName);
        Assertions.assertNotNull(CacheUser);
        //不缓存：查询db两次
        userName = "unless";
        cacheableService.unless(userName);
        CacheUser = cacheableService.unless(userName);
        Assertions.assertNotNull(CacheUser);
    }

    /**
     * 测试并发
     */
    @Test
    public void testToGather() throws InterruptedException {
        int count = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        String userName = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep((long) (Math.random()*1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cacheableService.toGather(userName);

            });
        }
        Thread.sleep(5000);
       // countDownLatch.await();
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
                    CacheUser CacheUser = cacheableService.testLockToGather(userName);
                    Assertions.assertNotNull(CacheUser);
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
