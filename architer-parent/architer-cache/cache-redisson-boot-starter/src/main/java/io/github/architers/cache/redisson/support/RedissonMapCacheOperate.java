package io.github.architers.cache.redisson.support;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.operate.*;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * hash缓存操作
 *
 * @author luyi
 */
public class RedissonMapCacheOperate implements CacheOperate {

    private final RedissonClient redissonClient;

    public RedissonMapCacheOperate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(PutParam putParam) {
        RMapCache<Object, Object> rMap = redissonClient.getMapCache(putParam.getCacheName());
        Object cacheValue = putParam.getCacheValue();
        //存在过期时间
        if (putParam.getExpireTime() > 0) {
            if (putParam.isAsync()) {
                rMap.putAsync(putParam.getKey(), cacheValue, putParam.getExpireTime(), putParam.getTimeUnit());
            } else {
                rMap.put(putParam.getKey(), cacheValue, putParam.getExpireTime(), putParam.getTimeUnit());
            }
            return;
        }
        if (putParam.isAsync()) {
            rMap.putAsync(putParam.getKey(), cacheValue);
        } else {
            rMap.put(putParam.getKey(), cacheValue);
        }
    }

    @Override
    public void delete(DeleteParam deleteParam) {
        if (deleteParam.isAsync()) {
            redissonClient.getMapCache(deleteParam.getCacheName()).removeAsync(deleteParam.getKey());
        } else {
            redissonClient.getMapCache(deleteParam.getCacheName()).removeAsync(deleteParam.getKey());
        }
    }

    @Override
    public Object get(GetParam getParam) {
        return redissonClient.getMapCache(getParam.getCacheName()).get(getParam.getKey());
    }

    @Override
    public void deleteAll(DeleteParam deleteParam) {
        if (deleteParam.isAsync()) {
            redissonClient.getMapCache(deleteParam.getCacheName()).delete();
        }

        if (redissonClient.getMapCache(deleteParam.getCacheName()).delete()) {
            throw new RuntimeException("删除缓存失败");
        }
    }

    @Override
    public void batchDelete(BatchDeleteParam batchDeleteParam) {
        Collection<?> keys = batchDeleteParam.getKeys();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        RMapCache<Object, Object> rMapCache = redissonClient.getMapCache(batchDeleteParam.getCacheName());
        rMapCache.keySet(keys.size()).clear();
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        Map<Object, Object> cacheMap =
                BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(),
                        Symbol.COLON);
        if (batchPutParam.isAsync()) {
            RMapCache<Object, Object> rMapCache = redissonClient.getMapCache(batchPutParam.getCacheName());
            if (batchPutParam.getExpireTime() > 0) {
                rMapCache.putAllAsync(cacheMap, batchPutParam.getExpireTime(), batchPutParam.getTimeUnit());
            } else {
                rMapCache.putAllAsync(cacheMap);
            }
            return;
        }
        RMapCache<Object, Object> rMapCache = redissonClient.getMapCache(batchPutParam.getCacheName());
        if (batchPutParam.getExpireTime() > 0) {
            rMapCache.putAll(cacheMap, batchPutParam.getExpireTime(), batchPutParam.getTimeUnit());
        } else {
            rMapCache.putAll(cacheMap);
        }
    }

}
