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
public class RedissonValueCacheOperate implements RemoteCacheOperate {


    private final RedissonClient redissonClient;

    public RedissonValueCacheOperate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(PutParam put) {
        String cacheKey = getCacheKey(put.getWrapperCacheName(),put.getKey());
        RBucket<Object> rBucket = redissonClient.getBucket(cacheKey);
        if (put.isAsync()) {
            this.putAsync(rBucket, put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
        } else {
            this.put(rBucket, put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
        }
    }

    private void put(RBucket<Object> rBucket, Object value, long expireTime, TimeUnit timeUnit) {

        if (value instanceof InvalidCacheValue) {
            //空值就防止一个空值，防止缓存穿透
            rBucket.set(value, 5, TimeUnit.MINUTES);
            return;
        }
        if (expireTime > 0) {
            //过期+同步
            rBucket.set(value, expireTime, timeUnit);
        } else {
            //不过期+同步
            rBucket.set(value);
        }
    }

    private void putAsync(RBucket<Object> rBucket, Object value, long expireTime, TimeUnit timeUnit) {
        if (value instanceof InvalidCacheValue) {
            //空值就防止一个空值，防止缓存穿透
            rBucket.set(value, 5, TimeUnit.MINUTES);
            return;
        }
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
        String bucketName = getCacheKey(delete.getWrapperCacheName(), delete.getKey());
        if (delete.isAsync()) {
            redissonClient.getBucket(bucketName).deleteAsync();
        } else {
            redissonClient.getBucket(bucketName).delete();
        }
    }

    @Override
    public Object get(GetParam getParam) {
        String bucketName = getCacheKey(getParam.getWrapperCacheName(), getParam.getKey());
        return redissonClient.getBucket(bucketName).get();
    }

    private String getCacheKey(String cacheName, String cacheKey) {
        return cacheName + ":" + cacheKey;
    }


    @Override
    public void batchDelete(BatchEvictParam batchEvictParam) {
        Collection<?> keys = batchEvictParam.getKeys();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        String[] cacheKeys = new String[keys.size()];
        int i = 0;
        for (Object key : keys) {
            String cacheKey = batchEvictParam.getWrapperCacheName() + Symbol.COLON +
                    JsonUtils.toJsonString(key);
            cacheKeys[i] = cacheKey;
            i++;
        }
        if (batchEvictParam.isAsync()) {
            redissonClient.getKeys().deleteAsync(cacheKeys);
        } else {
            redissonClient.getKeys().delete(cacheKeys);
        }
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {

        Map<String, Object> cacheMap = BatchValueUtils.parseValue2Map(batchPutParam.getWrapperCacheName(),
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
