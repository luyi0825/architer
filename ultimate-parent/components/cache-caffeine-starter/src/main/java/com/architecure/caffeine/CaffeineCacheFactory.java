package com.architecure.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Scheduler;
import org.checkerframework.checker.units.qual.K;
import org.springframework.cache.CacheManager;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine缓存工厂
 *
 * @author luyi
 */
public class CaffeineCacheFactory {

    Map<String, Cache<String, Object>> caches = new ConcurrentHashMap<>(2);

    public Cache<String, Object> get(String cacheName, long expireTime, TimeUnit timeUnit) {
        Cache<String, Object> loadingCache = caches.get(cacheName);
        if (loadingCache != null) {
            return loadingCache;
        }
        return newCache(cacheName, expireTime, timeUnit);
    }

    /**
     * @param cacheName
     * @param expireTime
     * @return
     */
    private Cache<String, Object> newCache(String cacheName, long expireTime, TimeUnit timeUnit) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().scheduler(Scheduler.systemScheduler());
        Cache<String, Object> cache;
        if (expireTime > 0) {
            cache = caffeine.expireAfterWrite(expireTime, timeUnit).build();
        } else {
            cache = caffeine.build();
        }
        caches.putIfAbsent(cacheName, cache);
        return caches.get(cacheName);

    }

}
