package io.github.architers.cache.redisson.support;

import io.github.architers.context.cache.Cache;
import io.github.architers.context.cache.operation.CacheOperate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;

import java.util.concurrent.ExecutionException;

@SpringBootTest
class MapCacheTest implements ApplicationContextAware {

    @Resource
    private CacheOperate cacheOperate;

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
        Cache cache = cacheOperate.getMapCache("mapCache");
        cacheTest = new CacheTest(cache);
    }
}