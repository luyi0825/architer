package io.github.architers.redisson.cache.support;

import io.github.architers.context.cache.operation.CacheOperate;
import io.github.architers.context.cache.operation.DeleteCacheParam;
import io.github.architers.context.cache.operation.GetCacheParam;
import io.github.architers.context.cache.operation.PutCacheParam;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

/**
 * @author luyi
 */
public class MapCacheOperate implements CacheOperate {

    private final RedissonClient redissonClient;

    public MapCacheOperate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void put(PutCacheParam putCacheParam) {
        RMapCache<Object, Object> rMap = redissonClient.getMapCache(putCacheParam.getCacheName());
        Object cacheValue = putCacheParam.getCacheValue();
        //存在过期时间
        if (putCacheParam.getExpireTime() > 0) {
            if (putCacheParam.isAsync()) {
                rMap.putAsync(putCacheParam.getKey(), cacheValue, putCacheParam.getExpireTime(), putCacheParam.getTimeUnit());
            } else {
                rMap.put(putCacheParam.getKey(), cacheValue, putCacheParam.getExpireTime(), putCacheParam.getTimeUnit());
            }
            return;
        }
        if (putCacheParam.isAsync()) {
            rMap.putAsync(putCacheParam.getKey(), cacheValue);
        } else {
            rMap.put(putCacheParam.getKey(), cacheValue);
        }
    }

    @Override
    public void delete(DeleteCacheParam deleteCacheParam) {
        if (deleteCacheParam.isAsync()) {
            redissonClient.getMapCache(deleteCacheParam.getCacheName()).removeAsync(deleteCacheParam.getKey());
        } else {
            redissonClient.getMapCache(deleteCacheParam.getCacheName()).removeAsync(deleteCacheParam.getKey());
        }
    }

    @Override
    public Object get(GetCacheParam getCacheParam) {
        return redissonClient.getMapCache(getCacheParam.getCacheName()).get(getCacheParam.getKey());
    }

    @Override
    public void deleteAll(DeleteCacheParam deleteCacheParam) {
        if (deleteCacheParam.isAsync()) {
            redissonClient.getMapCache(deleteCacheParam.getCacheName()).delete();
        }

        if (redissonClient.getMapCache(deleteCacheParam.getCacheName()).delete()) {
            throw new RuntimeException("删除缓存失败");
        }
    }
}
