package io.github.architers.cache.redisson.support;


import io.github.architers.context.Symbol;
import io.github.architers.context.cache.batch.BatchValueFactory;
import org.redisson.api.RBatch;
import org.redisson.api.RFuture;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * redis对应的map 类型缓存类
 *
 * @author luyi
 * @version 1.0.0
 */
public class MapCache extends BaseRedissonCache {

    private final RedisTemplateCacheService cacheService;

    BatchValueFactory batchValueFactory = new BatchValueFactory();


    private RedissonClient redissonClient;

    public MapCache(String cacheName, RedisTemplateCacheService redisTemplateCacheService,
                    RedissonClient redissonClient) {
        super(cacheName);
        this.cacheService = redisTemplateCacheService;
        this.redissonClient = redissonClient;
    }


    @Override
    public void set(Object key, Object value) {
        redissonClient.getMap(cacheName);
        cacheService.set(cacheName, key, value);
    }

    @Override
    public Future<Boolean> multiSet(Object values) {

        Map<Object, Object> cacheMap = batchValueFactory.parseValue2Map(values, Symbol.COLON)
        redissonClient.getMap(cacheName).putAll(cacheMap);
        return null;
    }

    @Override
    public Future<Boolean> multiSet(Object value, long expire, TimeUnit timeUnit) {
        multiSet(value);
        RMap<?, ?> rMap = redissonClient.getMap(cacheName);
        rMap.expireAsync(Duration.ofMillis(timeUnit.toMillis(expire)));
        return null;
    }

    @Override
    public void set(Object key, Object value, long expire, TimeUnit timeUnit) {
        cacheService.set(cacheName, key, value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(Object key, Object value) {
        return cacheService.setIfAbsent(cacheName, key, value);
    }

    @Override
    public Object get(Object key) {
        return cacheService.get(cacheName, key);
    }

    @Override
    public Map<Object, Object> multiGet(Set<Object> keys) throws ExecutionException, InterruptedException {
        RFuture<Map<Object, Object>> rFuture = redissonClient.getMap(cacheName).getAllAsync(keys);
        return rFuture.get();
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        return cacheService.get(cacheName, key, clazz);
    }

    @Override
    public boolean delete(Object key) {
        return cacheService.delete(cacheName, key) > 0;
    }

    @Override
    public long multiDelete(Collection<Object> keys) {
        Collection<Object> cacheKeys = batchValueFactory.parseCacheKeys(keys, Symbol.COLON);
        if (CollectionUtils.isEmpty(cacheKeys)) {
            return 0;
        }
        return cacheService.delete(cacheName, cacheKeys.toArray());
    }

    @Override
    public Map<Object, Object> getAll() {
        RBatch rBatch = redissonClient.createBatch();
        return redissonClient.getMap(cacheName);
    }

    @Override
    public void clearAll() {
        redissonClient.getMap(cacheName).clear();
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }


    @Override
    public boolean setIfAbsent(Object key, Object value, long expire, TimeUnit timeUnit) {
        return cacheService.setIfAbsent(cacheName, key, value, expire, timeUnit);
    }

    @Override
    public boolean setIfPresent(Object key, Object value) {
        return redissonClient.getMap(cacheName).fastPutIfAbsent(key, value);
    }

    @Override
    public boolean setIfPresent(Object key, Object value, long expire, TimeUnit timeUnit) {
        return redissonClient.getMap(cacheName).fastPutIfAbsent(key, value
        );
    }

    @Override
    public Object getAndSet(Object key, Object value) {
        return null;
    }


}
