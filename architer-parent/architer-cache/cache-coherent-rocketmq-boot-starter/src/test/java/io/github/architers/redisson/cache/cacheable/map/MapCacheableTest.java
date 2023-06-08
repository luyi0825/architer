package io.github.architers.redisson.cache.cacheable.map;

import io.github.architers.redisson.cache.CacheUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class MapCacheableTest {

    @Resource
    private MapCacheableService mapCacheableService;

    @Resource
    private RedissonClient redissonClient;


    /**
     * 测试一个@Cacheable注解
     */
    @Test
    public void testOneCacheable() {
        String userName = "123456789";
        long count = 10000;
        redissonClient.getMapCache("mapCacheableService_oneCacheable").put(userName,
                new CacheUser().setUsername(userName));
        //使用注解查询一万次
        long time1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            CacheUser CacheUser = mapCacheableService.oneCacheable(userName);
            Assertions.assertNotNull(CacheUser);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("cacheable耗时：" + (time2 - time1));
        for (int i = 0; i < count; i++) {
            CacheUser CacheUser = (CacheUser) redissonClient.getMapCache("mapCacheableService_oneCacheable").get(userName);
            Assertions.assertNotNull(CacheUser);
        }
        long time3 = System.currentTimeMillis();
        System.out.println("使用redissonClient耗时:" + (time3 - time2));
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
            CacheUser CacheUser = mapCacheableService.twoCacheable(userId);
            Assertions.assertNotNull(CacheUser);
        }
    }

    /**
     * 测试expireTime
     */
    @Test
    public void testExpireTime() throws InterruptedException {
        int count = 5;
        String userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            //不过期
            CacheUser CacheUser = mapCacheableService.expireTime_never(userId);
            Assertions.assertNotNull(CacheUser);
        }

        //3分钟过期
        userId = UUID.randomUUID().toString();
        for (int i = 0; i < count; i++) {
            CacheUser CacheUser = mapCacheableService.expireTime_1_minutes(userId);
            Assertions.assertNotNull(CacheUser);
        }
        System.out.println(redissonClient.getMapCache("mapCacheableService:expireTime_1_minutes").get(userId));

        Thread.sleep(1000 * 60);
        System.out.println(redissonClient.getMapCache("mapCacheableService:expireTime_1_minutes").get(userId));
        Thread.sleep(500);
    }

    /**
     * 测试随机时间
     */
    @Test
    public void testRandomTime() throws InterruptedException {
        int count = 100;
        for (int i = 0; i < count; i++) {
            String userName = UUID.randomUUID().toString();
            mapCacheableService.randomTime(userName);
            CacheUser CacheUser = mapCacheableService.randomTime(userName);
            Assertions.assertNotNull(CacheUser);
        }
        Thread.sleep(1500);
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
        mapCacheableService.condition(userName);
        CacheUser CacheUser = mapCacheableService.condition(userName);
        Assertions.assertNotNull(CacheUser);
        //长度小于10，查询，不缓存：查询db两次
        String userName2 = "999999999";
        mapCacheableService.condition(userName2);
        CacheUser = mapCacheableService.condition(userName2);
        Assertions.assertNotNull(CacheUser);
    }

    /**
     * 测试unless
     */
    @Test
    public void testUnless() {
        //缓存，查询1次
        String userName = "no_unless";
        mapCacheableService.unless(userName);
        CacheUser CacheUser = mapCacheableService.unless(userName);
        Assertions.assertNotNull(CacheUser);
        //不缓存：查询db两次
        userName = "unless";
        mapCacheableService.unless(userName);
        CacheUser = mapCacheableService.unless(userName);
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
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                mapCacheableService.toGather(userName);

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
                    CacheUser CacheUser = mapCacheableService.testLockToGather(userName);
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
