package com.architecure.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.Scheduler;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine缓存工厂
 *
 * @author luyi
 */
public class CaffeineCacheFactory {

    Map<String, Cache<String, Object>> graphs = new ConcurrentHashMap<>();

    public Cache<String, Object> get(String cacheName, long expireTime) {
        Cache<String, Object> loadingCache = graphs.get(cacheName);
        if (loadingCache != null) {
            return loadingCache;
        }
        return newCache(cacheName, expireTime);
    }

    /**
     * @param cacheName
     * @param expireTime
     * @return
     */
    private Cache<String, Object> newCache(String cacheName, long expireTime) {
        Cache<String, Object> cache = Caffeine.newBuilder().scheduler(Scheduler.systemScheduler()).maximumSize(15)
                //  .expireAfterWrite(expireTime, TimeUnit.SECONDS)
                .build();
        if (expireTime > 0) {
            cache.policy().expireAfterWrite().ifPresent(test -> {
                //test.setExpiresAfter(expireTime, TimeUnit.SECONDS);
            });
        }
        graphs.putIfAbsent(cacheName, cache);
        return graphs.get(cacheName);

    }

}
