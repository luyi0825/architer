package io.github.architers.cache.redisson.support;

import io.github.architers.cache.redisson.support.CacheTest;
import io.github.architers.cache.redisson.support.RedisCacheManager;
import io.github.architers.contenxt.cache.Cache;
import io.github.architers.contenxt.cache.CacheManager;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SimpleCacheTest implements ApplicationContextAware {

    @Autowired
    private RedisCacheManager redisCacheManager;

    private CacheTest cacheTest;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Cache cache = redisCacheManager.getSimpleCache("simpleCache");
        cacheTest = new CacheTest(cache);
    }

    @Test
    void set() {
        cacheTest.set();
    }

    @Test
    void multiSet() throws ExecutionException, InterruptedException {

        cacheTest.multiSet();
    }

    @Test
    void multiSetExpire() throws ExecutionException, InterruptedException {
        cacheTest.multiSetExpire();

    }

    @Test
    void setIfAbsent() {
        cacheTest.setIfAbsent();
    }

    @Test
    void testSetIfAbsent() {
        cacheTest.setIfAbsent();
    }

    @Test
    void setIfPresent() {
        cacheTest.setIfAbsent();
    }

    @Test
    void testSetIfPresent() {
    }

    @Test
    void getAndSet() {
        cacheTest.getAndSet();;
    }

    @Test
    void get() {

    }

    @Test
    void multiGet() throws ExecutionException, InterruptedException {
        cacheTest.multiGet();

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
    }

    @Test
    void clearAll() {
    }


}