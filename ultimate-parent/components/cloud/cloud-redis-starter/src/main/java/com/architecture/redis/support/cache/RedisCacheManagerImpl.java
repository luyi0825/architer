package com.architecture.redis.support.cache;

import com.architecture.context.cache.Cache;
import com.architecture.context.cache.CacheManager;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * redis缓存管理实现
 *
 * @author luyi
 */
public class RedisCacheManagerImpl implements CacheManager {

    private RedissonClient client;

    private RedisValueService valueOperations;

    public RedisCacheManagerImpl(RedissonClient client, RedisValueService valueOperations) {
        this.client = client;
        this.valueOperations = valueOperations;
    }

    ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>(32);

    @Override
    public Cache getSimpleCache(String cacheName) {
        //org.springframework.cache.CacheManager
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            return new ValueRedisCache(cacheName, valueOperations);
        }
        return cache;
    }

    @Override
    public Cache getMapCache(String cacheName) {
        return null;
    }

}
