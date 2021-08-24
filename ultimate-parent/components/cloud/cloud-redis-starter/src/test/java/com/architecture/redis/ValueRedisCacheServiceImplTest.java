package com.architecture.redis;

import com.architecture.context.cache.Cache;
import com.architecture.context.cache.CacheManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class ValueRedisCacheServiceImplTest {

    @Autowired
    private Cache cache;

    @Test
    void set() {
        cache.set("set44", 1);
        cache.set("set", new User().setName("test"));
    }

    @Test
    void testSet() {
        User user = new User().setName("testSet");
        cache.set("testSet", user, 2, TimeUnit.MINUTES);
    }

    @Test
    void setIfAbsent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cache.setIfAbsent(uuid, 1);
        Assertions.assertTrue(bool);
        bool = cache.setIfAbsent(uuid, 1);
        Assertions.assertFalse(bool);
    }

    @Test
    void testSetIfAbsent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cache.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
        Assertions.assertTrue(bool);
        bool = cache.setIfAbsent(uuid, 1, 2L, TimeUnit.MINUTES);
        Assertions.assertFalse(bool);
    }

    @Test
    void setIfPresent() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cache.setIfPresent(uuid, 1);
        Assertions.assertFalse(bool);
        bool = cache.setIfAbsent(uuid, 1);
        Assertions.assertTrue(bool);
    }

    @Test
    void testSetIfPresent1() {
        String uuid = UUID.randomUUID().toString();
        boolean bool = cache.setIfPresent(uuid, 1, 2, TimeUnit.MINUTES);
        Assertions.assertFalse(bool);
        bool = cache.setIfAbsent(uuid, 1, 2, TimeUnit.MINUTES);
        Assertions.assertTrue(bool);
    }

    @Test
    void getAndSet() {
        String uuid = UUID.randomUUID().toString();
        Object value = cache.getAndSet(uuid, "getAndSet");
        Assertions.assertNull(value);
        value = cache.getAndSet(uuid, "23");
        Assertions.assertEquals("getAndSet", value);
    }

    @Test
    void get() {
        String uuid = UUID.randomUUID().toString();
        cache.set(uuid, new User().setName(uuid));
        User user = (User) cache.get(uuid);
        Assertions.assertEquals(uuid, user.getName());
    }

    @Test
    void testGet() {
        String uuid = UUID.randomUUID().toString();
        cache.set(uuid, new User().setName(uuid));
        User user = cache.get(uuid, User.class);
        Assertions.assertEquals(uuid, user.getName());
    }

    @Test
    void delete() {
        String uuid = UUID.randomUUID().toString();
        cache.set(uuid, new User().setName(uuid));
        cache.delete(uuid);
    }


}