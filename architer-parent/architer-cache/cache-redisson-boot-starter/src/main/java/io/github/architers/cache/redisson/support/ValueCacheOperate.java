package io.github.architers.cache.redisson.support;

import io.github.architers.context.cache.operation.*;
import org.redisson.api.RedissonClient;

/**
 * @author luyi
 */
public class ValueCacheOperate implements CacheOperate {


    private RedissonClient redissonClient;

    @Override
    public void put(PutCacheParam put) {
        String cacheKey = getCacheKey(put);
        if (put.isAsync()) {
            redissonClient.getBucket(cacheKey).setAsync(put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
        } else {
            redissonClient.getBucket(cacheKey).set(put.getCacheValue(), put.getExpireTime(), put.getTimeUnit());
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
