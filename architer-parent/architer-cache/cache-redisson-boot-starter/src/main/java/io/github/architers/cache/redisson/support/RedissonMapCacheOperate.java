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
import java.util.concurrent.TimeUnit;

/**
 * hash缓存操作
 *
 * @author luyi
 */
public class RedissonMapCacheOperate implements RemoteCacheOperate {

    private final RedissonClient redissonClient;

    public RedissonMapCacheOperate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(PutParam putParam) {
        RMapCache<Object, Object> rMap = redissonClient.getMapCache(putParam.getWrapperCacheName());
        Object cacheValue = putParam.getCacheValue();
        if (cacheValue instanceof InvalidCacheValue) {
            //空值就防止一个空值，防止缓存穿透
            rMap.putIfAbsent(putParam.getKey(), cacheValue, 5, TimeUnit.MINUTES);
            return;
        }
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
            redissonClient.getMapCache(deleteParam.getWrapperCacheName()).removeAsync(deleteParam.getKey());
        } else {
            redissonClient.getMapCache(deleteParam.getWrapperCacheName()).removeAsync(deleteParam.getKey());
        }
    }

    @Override
    public Object get(GetParam getParam) {
        RMapCache<String, Object> rMapCache = redissonClient.getMapCache(getParam.getWrapperCacheName());
        return rMapCache.get(getParam.getKey());
    }

    @Override
    public void deleteAll(DeleteAllParam deleteAllParam) {
        if (deleteAllParam.isAsync()) {
            redissonClient.getMapCache(deleteAllParam.getWrapperCacheName()).delete();
        }

        if (redissonClient.getMapCache(deleteAllParam.getWrapperCacheName()).delete()) {
            throw new RuntimeException("删除缓存失败");
        }
    }

    @Override
    public void batchDelete(BatchEvictParam batchEvictParam) {
        Collection<?> keys = batchEvictParam.getKeys();
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        RMapCache<Object, Object> rMapCache = redissonClient.getMapCache(batchEvictParam.getWrapperCacheName());
        rMapCache.keySet(keys.size()).clear();
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        Map<Object, Object> cacheMap =
                BatchValueUtils.parseValue2Map(batchPutParam.getBatchCacheValue(),
                        Symbol.COLON);
        if (batchPutParam.isAsync()) {
            RMapCache<Object, Object> rMapCache = redissonClient.getMapCache(batchPutParam.getWrapperCacheName());
            if (batchPutParam.getExpireTime() > 0) {
                rMapCache.putAllAsync(cacheMap, batchPutParam.getExpireTime(), batchPutParam.getTimeUnit());
            } else {
                rMapCache.putAllAsync(cacheMap);
            }
            return;
        }
        RMapCache<Object, Object> rMapCache = redissonClient.getMapCache(batchPutParam.getWrapperCacheName());
        if (batchPutParam.getExpireTime() > 0) {
            rMapCache.putAll(cacheMap, batchPutParam.getExpireTime(), batchPutParam.getTimeUnit());
        } else {
            rMapCache.putAll(cacheMap);
        }
    }

}
