package io.github.architers.cache.caffeine.support;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.architers.cache.caffeine.CaffeineCacheFactory;
import io.github.architers.cache.caffeine.ExpireTimeLocal;
import io.github.architers.context.Symbol;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.utils.JsonUtils;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * hash缓存操作
 *
 * @author luyi
 */
public class CaffeineMapCacheOperate implements CacheOperate {


    private final CaffeineCacheFactory caffeineCacheFactory;


    public CaffeineMapCacheOperate(CaffeineCacheFactory caffeineCacheFactory) {
        this.caffeineCacheFactory = caffeineCacheFactory;
    }

    @Override
    public void put(PutParam putParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(putParam.getCacheName());

        Object cacheValue = putParam.getCacheValue();
        try {
            //存在过期时间
            if (putParam.getExpireTime() > 0) {
                ExpireTimeLocal.set(SECONDS.convert(putParam.getExpireTime(), putParam.getTimeUnit()));
                cache.put(putParam.getKey(), cacheValue);
            } else {
                cache.put(putParam.getKey(), cacheValue);
            }
        } finally {
            ExpireTimeLocal.remove();
        }


    }

    @Override
    public void delete(DeleteParam deleteParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(deleteParam.getCacheName());
        cache.invalidate(deleteParam.getKey());
    }

    @Override
    public Object get(GetParam getParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(getParam.getCacheName());
        return cache.getIfPresent(getParam.getKey());
    }

    @Override
    public void deleteAll(DeleteParam deleteParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(deleteParam.getCacheName());
        cache.cleanUp();
    }

    @Override
    public void batchDelete(BatchDeleteParam batchDeleteParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(batchDeleteParam.getCacheName());
        cache.invalidateAll( new HashSet<String>((Collection<? extends String>) batchDeleteParam.getKeys()));
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        Cache<String, Object> cache = caffeineCacheFactory.getCache(batchPutParam.getCacheName());
        Map<Object, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(), Symbol.COLON);
        cacheMap.forEach((key, value) -> cache.put(JsonUtils.toJsonString(key), value));
    }

}
