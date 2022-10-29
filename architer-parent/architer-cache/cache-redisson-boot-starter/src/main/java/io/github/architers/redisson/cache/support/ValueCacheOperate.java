package io.github.architers.redisson.cache.support;

import io.github.architers.context.cache.operation.*;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

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
}
