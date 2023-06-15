package io.github.architers.cache.caffeine.support;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.architers.cache.caffeine.CaffeineCacheFactory;
import io.github.architers.cache.caffeine.ExpireTimeLocal;
import io.github.architers.context.cache.CacheConstants;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.utils.JsonUtils;


import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * hash缓存操作
 *
 * @author luyi
 */
public class CaffeineMapCacheOperate implements LocalCacheOperate {


    private final CaffeineCacheFactory caffeineCacheFactory;


    public CaffeineMapCacheOperate(CaffeineCacheFactory caffeineCacheFactory) {
        this.caffeineCacheFactory = caffeineCacheFactory;
    }

    @Override
    public void put(PutParam putParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(putParam.getWrapperCacheName());

        Object cacheValue = putParam.getCacheValue();

        try {
            if (cacheValue instanceof InvalidCacheValue) {
                //空值就防止一个空值，防止缓存穿透
                ExpireTimeLocal.set(MILLISECONDS.convert(5, TimeUnit.MINUTES));
                cache.put(putParam.getKey(), cacheValue);
            } else {
                //存在过期时间
                if (putParam.getExpireTime() > 0) {
                    ExpireTimeLocal.set(MILLISECONDS.convert(putParam.getExpireTime(), putParam.getTimeUnit()));
                    cache.put(putParam.getKey(), cacheValue);
                } else {
                    ExpireTimeLocal.set(-1L);
                    cache.put(putParam.getKey(), cacheValue);
                }
            }

        } finally {
            ExpireTimeLocal.remove();
        }


    }

    @Override
    public void delete(EvictParam evictParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(evictParam.getWrapperCacheName());
        cache.invalidate(evictParam.getKey());
    }

    @Override
    public Object get(GetParam getParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(getParam.getWrapperCacheName());
//        System.out.println(cache.getIfPresent(getParam.getKey()));
//        System.out.println(cache.getIfPresent(getParam.getKey()));
//        System.out.println(cache.getIfPresent(getParam.getKey()));
        return cache.getIfPresent(getParam.getKey());
    }

    @Override
    public void deleteAll(EvictAllParam evictAllParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(evictAllParam.getWrapperCacheName());
        cache.cleanUp();
    }

    @Override
    public void batchDelete(BatchEvictParam batchEvictParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(batchEvictParam.getWrapperCacheName());
        cache.invalidateAll(new HashSet<String>((Collection<? extends String>) batchEvictParam.getKeys()));
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(batchPutParam.getWrapperCacheName());
        Map<Object, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(), CacheConstants.CACHE_SPLIT);
        cacheMap.forEach((key, value) -> cache.put(JsonUtils.toJsonString(key), value));
    }

}
