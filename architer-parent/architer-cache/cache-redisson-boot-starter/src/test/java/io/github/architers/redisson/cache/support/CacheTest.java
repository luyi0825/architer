package io.github.architers.redisson.cache.support;

import io.github.architers.redisson.cache.CacheUser;
import io.github.architers.context.cache.Cache;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CacheTest {

    private Cache cache;

    public CacheTest(Cache cache) {
        this.cache = cache;
    }


    @Test
    void set() {
        cache.set(UUID.randomUUID(), UUID.randomUUID());
        cache.set("1", "3");
        CacheUser cacheUser = new CacheUser();
        cacheUser.setUsername(UUID.randomUUID().toString());
        cache.set(cacheUser.getUsername(), cacheUser);
    }

    @Test
    void multiSet() throws ExecutionException, InterruptedException {
        //设置map
        Map<String, Object> mapValue = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String uuid = String.valueOf(UUID.randomUUID());
            mapValue.put(uuid, uuid);
        }
        Future<Boolean> future = cache.multiSet(mapValue);
        System.out.println(future != null ? future.get() : "");
        //设置对象
        List<CacheUser> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CacheUser cacheUser = new CacheUser();
            cacheUser.setUsername("username" + i);
            cacheUser.setCity("city");
            list.add(cacheUser);
        }
        future = cache.multiSet(list);
        System.out.println(future != null ? future.get() : "");
        Thread.sleep(2000);
    }

    @Test
    void multiSetExpire() throws ExecutionException, InterruptedException {
        Map<String, Object> mapValue = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String uuid = String.valueOf(UUID.randomUUID());
            mapValue.put(uuid, uuid);
        }
        Future<Boolean> future = cache.multiSet(mapValue, 1, TimeUnit.DAYS);
        Thread.sleep(1000);

        //设置对象
        List<CacheUser> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CacheUser cacheUser = new CacheUser();
            cacheUser.setUsername("username" + i);
            cacheUser.setCity("city-phone");
            list.add(cacheUser);
        }
        future = cache.multiSet(list, 1, TimeUnit.DAYS);
        Thread.sleep(1000);
    }

    @Test
    void setIfAbsent() {
        System.out.println(cache.setIfAbsent(1, 1));
    }

    @Test
    void testSetIfAbsent() {
        System.out.println(cache.setIfAbsent(2, 2, 1, TimeUnit.DAYS));
    }

    @Test
    void setIfPresent() {
    }

    @Test
    void testSetIfPresent() {
    }

    @Test
    void getAndSet() {
    }

    @Test
    void get() {
        CacheUser cacheUser = new CacheUser();
        cacheUser.setUsername("username");
        cacheUser.setCity("city-get");
        cache.set("1234", cacheUser);
        cache.set("1235", cacheUser);
        CacheUser object = (CacheUser) cache.get("1234");
        System.out.println(object);
    }

    @Test
    void multiGet() throws ExecutionException, InterruptedException {
        Set<Object> set = new HashSet<>();
        set.add("1234");
        set.add("1235");
        Map<?, Object> map = cache.multiGet(set);
        System.out.println(map);
    }

    @Test
    void testGet() {
    }

    @Test
    void delete() {
    }

    @Test
    void multiDelete() {
    }

    @Test
    void getAll() {
        System.out.println(cache.getAll());
    }

    @Test
    void clearAll() {
    }

}
