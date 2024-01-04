package io.github.architers.cache.caffeine;

import com.github.benmanes.caffeine.cache.*;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CaffeineCacheFactory {

    private final Map<String/*缓存名称*/, Cache<String/*缓存key*/, Serializable>> caches = new ConcurrentHashMap<>(32);

    private final ExpireAfter expireAfter;


    private final CaffeineProperties cacheProperties;

    public CaffeineCacheFactory(ExpireAfter expireAfter, CaffeineProperties cacheProperties) {
        this.expireAfter = expireAfter;
        this.cacheProperties = cacheProperties;
    }


    public Cache<String, Serializable> getCache(String cacheName) {
        Cache<String, Serializable> cache = caches.get(cacheName);
        if (cache == null) {
            return caches.computeIfAbsent(cacheName, this::buildCache);
        }
        return cache;

    }

    private Cache<String, Serializable> buildCache(String cacheName) {
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
        CaffeineConfig finalCaffeineConfig = caffeineConfig;
        caffeine.evictionListener(new RemovalListener<String, Serializable>() {
            @Override
            public void onRemoval(@Nullable String key, @Nullable Serializable serializable, RemovalCause removalCause) {
                if (finalCaffeineConfig.isPrintEvictLog()) {
                    log.warn("caffeine缓存驱逐:{}-{}", cacheName, key);
                }
            }
        });

        caffeine.removalListener(new RemovalListener<String, Serializable>() {
            @Override
            public void onRemoval(@Nullable String key, @Nullable Serializable serializable, RemovalCause removalCause) {
                if (finalCaffeineConfig.isPrintEvictLog()) {
                    log.warn("caffeine缓存驱逐:{}-{}", cacheName, key);
                }
            }
        });
        //caffeine.recordStats().refreshAfterWrite(1, TimeUnit.MICROSECONDS);
        return caffeine.expireAfter(expireAfter).build();
    }


}
