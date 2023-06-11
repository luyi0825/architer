package io.github.architers.cache.caffeine;

import com.github.benmanes.caffeine.cache.*;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.util.CollectionUtils;


import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * Caffeine缓存工厂
 *
 * @author luyi
 */
public class CaffeineCacheFactory {

    private ExpireAfter expireAfter;


    private final CaffeineProperties cacheProperties;

    public CaffeineCacheFactory(ExpireAfter expireAfter,CaffeineProperties cacheProperties) {
        this.expireAfter = expireAfter;
        this.cacheProperties = cacheProperties;
    }


    Map<String, Cache<String, Object>> caches = new ConcurrentHashMap<>(32);

    public Cache<String, Object> getCache(String cacheName) {
        Cache<String, Object> cache = caches.get(cacheName);
        if (cache == null) {
            return caches.computeIfAbsent(cacheName, this::buildCache);
        }
        return cache;

    }

    private Cache<String, Object> buildCache(String cacheName) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        Map<String, CaffeineConfig> caches = cacheProperties.getCaches();
        CaffeineConfig caffeineConfig;
        if (CollectionUtils.isEmpty(caches) || ((caffeineConfig = caches.get(cacheName)) == null)) {
            //没有配置这个缓存名称的配置，就new一个默认的
            caffeineConfig = new CaffeineConfig();
        }
        if (caffeineConfig.getMaximumSize() > 0) {
            caffeine.maximumSize(caffeineConfig.getMaximumSize());
        }
        if (caffeineConfig.getMaximumWeight() > 0) {
            caffeine.maximumWeight(caffeineConfig.getMaximumWeight());
        }
        caffeine.scheduler(Scheduler.systemScheduler());
        caffeine.initialCapacity(caffeineConfig.getInitialCapacity());

        //caffeine.recordStats().refreshAfterWrite(1, TimeUnit.MICROSECONDS);
        return caffeine.expireAfter(expireAfter).build();
    }


}
