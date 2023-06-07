package io.github.architers.cache.caffeine.support;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.architers.cache.caffeine.CaffeineCacheFactory;
import io.github.architers.cache.caffeine.ExpireTimeLocal;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.CacheOperate;


import java.io.Serializable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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
                ExpireTimeLocal.set(MILLISECONDS.convert(putParam.getExpireTime(), putParam.getTimeUnit()));
                cache.put(putParam.getCacheName(), cacheValue);
            } else {
                cache.put(putParam.getCacheName(), cacheValue);
            }
        } finally {
            ExpireTimeLocal.remove();
        }


    }

    @Override
    public void delete(DeleteParam deleteParam) {

    }

    @Override
    public Object get(GetParam getParam) {
        return null;

    }

    @Override
    public void deleteAll(DeleteParam deleteParam) {

    }

    @Override
    public void batchDelete(BatchDeleteParam batchDeleteParam) {

    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {

    }

}
