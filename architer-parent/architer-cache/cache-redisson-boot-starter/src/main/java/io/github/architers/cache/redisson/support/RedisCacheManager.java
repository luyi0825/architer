package io.github.architers.cache.redisson.support;


import io.github.architers.contenxt.cache.Cache;
import io.github.architers.contenxt.cache.CacheManager;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * redis缓存管理实现-基于redisson
 * <li>@see caches:将Cache实现类缓存起来。首次并发访问同一个CacheName情况下，这个CacheName可能会创建多个Cache实例
 * ，但是并无大碍。 </li>
 *
 * @author luyi
 */
public class RedisCacheManager implements CacheManager {


    private RedisTemplateCacheService redisTemplateCacheService;

    private RedissonClient redissonClient;


    public RedisCacheManager() {

    }


    public RedisCacheManager setRedisTemplateService(RedisTemplateCacheService redisTemplateCacheService) {
        this.redisTemplateCacheService = redisTemplateCacheService;
        return this;
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }

    public RedisCacheManager setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
        return this;
    }


    ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<>(32);

    @Override
    public Cache getSimpleCache(String cacheName) {
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            cache = new SimpleCache(cacheName, redissonClient, redisTemplateCacheService);
            caches.putIfAbsent(cacheName, cache);
        }
        caches.putIfAbsent(cacheName, cache);
        return cache;
    }

    @Override
    public Cache getMapCache(String cacheName) {
        Cache cache = caches.get(cacheName);
        if (cache == null) {
            cache = new MapCache(cacheName, redisTemplateCacheService, redissonClient);
            caches.putIfAbsent(cacheName, cache);
        }
        return cache;
    }

}
