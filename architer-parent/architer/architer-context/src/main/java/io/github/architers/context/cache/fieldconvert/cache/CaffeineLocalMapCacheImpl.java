//package io.github.architers.context.cache.convert.cache;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.RemovalCause;
//import com.github.benmanes.caffeine.cache.RemovalListener;
//import lombok.extern.slf4j.Slf4j;
//import org.checkerframework.checker.nullness.qual.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import java.io.Serializable;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//
///**
// * Caffeine本地缓存实现类
// *
// * @author luyi
// */
//@Component
//@Slf4j
//public class CaffeineLocalMapCacheImpl implements ICache {
//    private final Map<String, Cache<String, Serializable>> cacheMap = new ConcurrentHashMap<>(64);
//
//    @Override
//    public Serializable get(String cacheName, String key) {
//        Cache<String, Serializable> cache = cacheMap.get(cacheName);
//        if (cache == null) {
//            return null;
//        }
//        return cache.getIfPresent(key);
//    }
//
//    @Override
//    public Map<String, Serializable> getAll(String cacheName, Set<String> keys) {
//        Cache<String, Serializable> cache = cacheMap.get(cacheName);
//        return cache.getAllPresent(keys);
//    }
//
//    @Override
//    public void put(String cacheName, String key, Serializable value, Long expireTime, TimeUnit timeUnit) {
//        Cache<String, Serializable> cache = getNotNullLocalCache(cacheName);
//        cache.put(key, value);
//    }
//
//    @Override
//    public void putAll(String cacheName, Map<String, Serializable> cacheData, Long expireTime, TimeUnit timeUnit) {
//        Cache<String, Serializable> cache = getNotNullLocalCache(cacheName);
//        cache.putAll(cacheData);
//    }
//
//    @Override
//    public void removeAll(String cacheName) {
//        Cache<String, Serializable> cache = cacheMap.get(cacheName);
//        if (cache != null) {
//            cache.cleanUp();
//        }
//    }
//
//    @Override
//    public void remove(String cacheName, Set<String> keys) {
//        if (CollectionUtils.isEmpty(keys)) {
//            return;
//        }
//        Cache<String, Serializable> cache = cacheMap.get(cacheName);
//        if (cache != null) {
//            cache.invalidateAll(keys);
//        }
//    }
//
//    private Cache<String, Serializable> getNotNullLocalCache(String cacheName) {
//        Cache<String, Serializable> cache = cacheMap.get(cacheName);
//        if (cache == null) {
//            cache = Caffeine.newBuilder().maximumSize(2500).initialCapacity(200).removalListener(new RemovalListener<String, Object>() {
//                @Override
//                public void onRemoval(@Nullable String s, @Nullable Object o, RemovalCause removalCause) {
//                    log.info("removal本地缓存:{}", s);
//                }
//            }).build();
//        }
//        Cache<String, Serializable> preCache = cacheMap.putIfAbsent(cacheName, cache);
//        if (preCache != null) {
//            cache = preCache;
//        }
//        return cache;
//    }
//}
