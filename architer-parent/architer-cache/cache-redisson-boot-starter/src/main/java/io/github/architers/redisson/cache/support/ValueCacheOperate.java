package io.github.architers.redisson.cache.support;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.BatchValueUtils;
import io.github.architers.context.cache.operation.*;
import io.github.architers.context.utils.JsonUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author luyi
 */
public class ValueCacheOperate implements CacheOperate {


    private final RedissonClient redissonClient;

    public ValueCacheOperate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(PutCacheParam put) {
        String cacheKey = getCacheKey(put);
        RBucket<Object> rBucket = redissonClient.getBucket(cacheKey);
        if (put.getExpireTime() > 0) {
            if (put.isAsync()) {
                //过期+异步
                rBucket.setAsync(put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
            } else {
                //过期+同步步
                rBucket.set(put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
            }
            return;
        }
        if (put.isAsync()) {
            //不过期+异步
            rBucket.setAsync(put.getCacheValue());
        } else {
            //不过期+同步
            rBucket.set(put.getCacheValue());
        }
    }

    @Override
    public void delete(DeleteCacheParam delete) {
        String cacheKey = getCacheKey(delete);
        if (delete.isAsync()) {
            redissonClient.getBucket(cacheKey).deleteAsync();
        } else {
            redissonClient.getBucket(cacheKey).delete();
        }
    }

    @Override
    public Object get(GetCacheParam get) {
        return redissonClient.getBucket(getCacheKey(get)).get();
    }

    private String getCacheKey(BaseCacheOperationParam cacheOperationParam) {
        return cacheOperationParam.getCacheName() + ":" + cacheOperationParam.getKey();
    }

    @Override
    public void batchDelete(BatchDeleteParam batchDeleteParam) {
        Collection<?> keys = batchDeleteParam.getKeys();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        String[] cacheKeys = new String[keys.size()];
        int i = 0;
        for (Object key : keys) {
            StringBuilder cacheKey = new StringBuilder(batchDeleteParam.getCacheName());
            cacheKey.append(Symbol.COLON);
            cacheKey.append(JsonUtils.toJsonString(key));
            cacheKeys[i] = cacheKey.toString();
            i++;
        }
        if (batchDeleteParam.isAsync()) {
            redissonClient.getKeys().deleteAsync(cacheKeys);
        } else {
            redissonClient.getKeys().delete(cacheKeys);
        }
    }
}
