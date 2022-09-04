package io.github.architers.cache.redisson.support;

import io.github.architers.cache.redisson.CacheUser;
import io.github.architers.contenxt.cache.Cache;
import io.github.architers.contenxt.cache.CacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MapCacheTest implements ApplicationContextAware {

    @Resource
    private CacheManager cacheManager;

    private CacheTest cacheTest;

    @Test
    void set() {
        cacheTest.set();
    }

    @Test
    void multiSet() throws ExecutionException, InterruptedException {
        cacheTest.multiSet();
        cacheTest.multiSetExpire();
    }


    @Test
    void setIfAbsent() {
        cacheTest.setIfAbsent();
    }

    @Test
    void get() {
        cacheTest.get();
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
        cacheTest.getAll();
    }

    @Test
    void clearAll() {
    }

    @Test
    void getCacheName() {
    }

    @Test
    void testSetIfAbsent() {
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Cache cache = cacheManager.getMapCache("mapCache");
        cacheTest = new CacheTest(cache);
    }
}