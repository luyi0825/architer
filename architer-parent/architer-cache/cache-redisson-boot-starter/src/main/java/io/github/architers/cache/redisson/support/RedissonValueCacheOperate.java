package io.github.architers.cache.redisson.support;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.cache.utils.CacheUtils;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.*;
import io.github.architers.context.utils.JsonUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * String类型缓存操作
 *
 * @author luyi
 */
public class RedissonValueCacheOperate implements CacheOperate {


    private final RedissonClient redissonClient;

    public RedissonValueCacheOperate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(PutParam put) {
        String cacheKey = getCacheKey(put);
        RBucket<Object> rBucket = redissonClient.getBucket(cacheKey);
        if (put.isAsync()) {
            this.putAsync(rBucket, put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
        } else {
            this.put(rBucket, put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
        }
    }

    private void put(RBucket<Object> rBucket, Object value, long expireTime, TimeUnit timeUnit) {
        if (expireTime > 0) {
            //过期+同步步
            rBucket.set(value, expireTime, timeUnit);
        } else {
            //不过期+同步
            rBucket.set(value);
        }
    }

    private void putAsync(RBucket<Object> rBucket, Object value, long expireTime, TimeUnit timeUnit) {
        if (expireTime > 0) {
            //过期+异步
            rBucket.setAsync(value, expireTime, timeUnit);
            return;
        }
        //不过期+异步
        rBucket.setAsync(value);
    }


    @Override
    public void delete(DeleteParam delete) {
        String cacheKey = getCacheKey(delete);
        if (delete.isAsync()) {
            redissonClient.getBucket(cacheKey).deleteAsync();
        } else {
            redissonClient.getBucket(cacheKey).delete();
        }
    }

    @Override
    public Object get(GetParam get) {
        return redissonClient.getBucket(getCacheKey(get)).get();
    }

    private String getCacheKey(BaseCacheOperationParam cacheOperationParam) {
        return cacheOperationParam.getCacheName() + ":" + cacheOperationParam.getKey();
    }

    private String getCacheKey(String cacheName, Object key) {
        return cacheName + ":" + JsonUtils.toJsonString(key);
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
            String cacheKey = batchDeleteParam.getCacheName() + Symbol.COLON +
                    JsonUtils.toJsonString(key);
            cacheKeys[i] = cacheKey;
            i++;
        }
        if (batchDeleteParam.isAsync()) {
            redissonClient.getKeys().deleteAsync(cacheKeys);
        } else {
            redissonClient.getKeys().delete(cacheKeys);
        }
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {

        Map<String, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getCacheName(),
                Symbol.COLON,
                batchPutParam.getBatchCacheValue());
        long expireTime = CacheUtils.getExpireTime(batchPutParam.getExpireTime(),
                batchPutParam.getRandomTime());
        if (batchPutParam.getExpireTime() > 0) {
            cacheMap.forEach((cacheKey, value) -> {
                RBucket<Object> rBucket = redissonClient.getBucket(cacheKey);
                if (batchPutParam.isAsync()) {
                    this.putAsync(rBucket, value, expireTime, batchPutParam.getTimeUnit());
                } else {
                    this.put(rBucket, value, expireTime, batchPutParam.getTimeUnit());
                }
            });
            return;
        }
        if (batchPutParam.isAsync()) {
            redissonClient.getBuckets().setAsync(cacheMap);
        } else {
            redissonClient.getBuckets().set(cacheMap);
        }
    }
}
