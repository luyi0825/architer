package com.architecture.redis;

import com.architecture.context.cache.CacheService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class RedisCacheServiceImplTest {

    @Autowired
    private CacheService cacheService;

    @Test
    void set() {
        cacheService.set("set44", 1);
        cacheService.set("set", new User().setName("test"));
    }

    @Test
    void testSet() {
        User user = new User().setName("testSet");
        cacheService.set("testSet", user, 2, TimeUnit.MINUTES);
    }

    @Test
    void setIfAbsent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cacheService.setIfAbsent(uuid, 1);
        Assertions.assertTrue(bool);
        bool = cacheService.setIfAbsent(uuid, 1);
        Assertions.assertFalse(bool);
    }

    @Test
    void testSetIfAbsent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cacheService.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
        Assertions.assertTrue(bool);
        bool = cacheService.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
        Assertions.assertFalse(bool);
    }

    @Test
    void setIfPresent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cacheService.setIfPresent(uuid, 1);
        Assertions.assertFalse(bool);
        bool = cacheService.setIfAbsent(uuid, 1);
        Assertions.assertTrue(bool);
    }

    @Test
    void testSetIfPresent1() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cacheService.setIfPresent(uuid, 1, 2, TimeUnit.MINUTES);
        Assertions.assertFalse(bool);
        bool = cacheService.setIfAbsent(uuid, 1, 2, TimeUnit.MINUTES);
        Assertions.assertTrue(bool);
    }

    @Test
    void getAndSet() {
        String uuid = UUID.randomUUID().toString();
        Object value = cacheService.getAndSet(uuid, "getAndSet");
        Assertions.assertNull(value);
        value = cacheService.getAndSet(uuid, "23");
        Assertions.assertEquals("getAndSet", value);
    }

    @Test
    void get() {
        String uuid = UUID.randomUUID().toString();
        cacheService.set(uuid, new User().setName(uuid));
        User user = (User) cacheService.get(uuid);
        Assertions.assertEquals(uuid, user.getName());
    }

    @Test
    void testGet() {
        String uuid = UUID.randomUUID().toString();
        cacheService.set(uuid, new User().setName(uuid));
        User user = cacheService.get(uuid, User.class);
        Assertions.assertEquals(uuid, user.getName());
    }

    @Test
    void delete() {
        String uuid = UUID.randomUUID().toString();
        cacheService.set(uuid, new User().setName(uuid));
        cacheService.delete(uuid);
    }


}