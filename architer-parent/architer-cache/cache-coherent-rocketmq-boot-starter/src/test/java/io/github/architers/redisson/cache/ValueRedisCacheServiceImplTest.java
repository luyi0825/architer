//package io.github.architers.cache.redisson;
//
//
//import io.github.architers.context.Symbol;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
///**
// * RedisValueService测试
// *
// * @author luyi
// */
//@SpringBootTest
//class ValueRedisCacheServiceImplTest {
//
//    @Autowired
//    private RedisTemplateCacheService redisTemplateCacheService;
//
//    private String prefix = this.getClass().getName();
//
//    @Test
//    void set() {
//        String set1 = UUID.randomUUID().toString(), set2 = UUID.randomUUID().toString(), value = "32";
//        redisTemplateCacheService.set(set1, value);
//        Assertions.assertEquals(value, redisTemplateCacheService.get(set1));
//        redisTemplateCacheService.set(set2, new io.github.architers.cache.redisson.CacheUser().setName(value));
//        io.github.architers.cache.redisson.CacheUser cacheUser = (io.github.architers.cache.redisson.CacheUser) redisTemplateCacheService.get(set2);
//        Assertions.assertEquals(value, cacheUser.getName());
//    }
//
//    @Test
//    void testSet() {
//        String key = "testSet" + Symbol.COLON + UUID.randomUUID();
//        io.github.architers.cache.redisson.CacheUser cacheUser = new io.github.architers.cache.redisson.CacheUser().setName("testSet");
//        redisTemplateCacheService.set(key, cacheUser, 2, TimeUnit.MINUTES);
//        Assertions.assertEquals(2, redisTemplateCacheService.getExpireTime(key, TimeUnit.MINUTES));
//    }
//
//    @Test
//    void setIfAbsent() {
//        String uuid = prefix + "_setIfAbsent" + Symbol.COLON + UUID.randomUUID().toString();
//        boolean bool = redisTemplateCacheService.setIfAbsent(uuid, 1);
//        Assertions.assertTrue(bool);
//        bool = redisTemplateCacheService.setIfAbsent(uuid, 1);
//        Assertions.assertFalse(bool);
//    }
//
//    @Test
//    void testSetIfAbsent() {
//        String uuid = UUID.randomUUID().toString();
//        boolean bool = redisTemplateCacheService.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
//        Assertions.assertTrue(bool);
//        bool = redisTemplateCacheService.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
//        Assertions.assertFalse(bool);
//    }
//
//    @Test
//    void setIfPresent() {
//        String uuid = UUID.randomUUID().toString();
//        boolean bool = redisTemplateCacheService.setIfPresent(uuid, 1);
//        Assertions.assertFalse(bool);
//        bool = redisTemplateCacheService.setIfAbsent(uuid, 1);
//        Assertions.assertTrue(bool);
//    }
//
//    @Test
//    void testSetIfPresent1() {
//        String uuid = UUID.randomUUID().toString();
//        boolean bool = redisTemplateCacheService.setIfPresent(uuid, 1, 2, TimeUnit.MINUTES);
//        Assertions.assertFalse(bool);
//        bool = redisTemplateCacheService.setIfAbsent(uuid, 1, 2, TimeUnit.MINUTES);
//        Assertions.assertTrue(bool);
//    }
//
//    @Test
//    void getAndSet() {
//        String uuid = UUID.randomUUID().toString();
//        Object value = redisTemplateCacheService.getAndSet(uuid, "getAndSet");
//        Assertions.assertNull(value);
//        value = redisTemplateCacheService.getAndSet(uuid, "23");
//        Assertions.assertEquals("getAndSet", value);
//    }
//
//    @Test
//    void get() {
//        String uuid = UUID.randomUUID().toString();
//        redisTemplateCacheService.set(uuid, new io.github.architers.cache.redisson.CacheUser().setName(uuid));
//        io.github.architers.cache.redisson.CacheUser cacheUser = (io.github.architers.cache.redisson.CacheUser) redisTemplateCacheService.get(uuid);
//        Assertions.assertEquals(uuid, cacheUser.getName());
//    }
//
//    @Test
//    void testGet() {
//        String uuid = UUID.randomUUID().toString();
//        redisTemplateCacheService.set(uuid, new io.github.architers.cache.redisson.CacheUser().setName(uuid));
//        io.github.architers.cache.redisson.CacheUser cacheUser = redisTemplateCacheService.get(uuid, io.github.architers.cache.redisson.CacheUser.class);
//        Assertions.assertEquals(uuid, cacheUser.getName());
//    }
//
//    @Test
//    void delete() {
//        String uuid = UUID.randomUUID().toString();
//        redisTemplateCacheService.set(uuid, new io.github.architers.cache.redisson.CacheUser().setName(uuid));
//        redisTemplateCacheService.delete(uuid);
//    }
//
//
//}