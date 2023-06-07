package io.github.architers.cache.caffeine;

import com.github.benmanes.caffeine.cache.*;

import org.springframework.util.CollectionUtils;


import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Caffeine缓存工厂
 *
 * @author luyi
 */
public class CaffeineCacheFactory {

    private ExpireAfter expireAfter;


    private final CaffeineProperties cacheProperties;

    public CaffeineCacheFactory( ExpireAfter expireAfter) {
        this.cacheProperties = expireAfter.getCaffeineProperties();
        this.expireAfter = expireAfter;
    }


    Map<String, Cache<String, Object>> caches = new ConcurrentHashMap<>(2);

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
        return caffeine.expireAfter(expireAfter).build();
    }


}
