package io.github.architers.cache.redisson.support;

import io.github.architers.context.cache.operation.CacheOperate;
import io.github.architers.context.cache.operation.DeleteCacheParam;
import io.github.architers.context.cache.operation.GetCacheParam;
import io.github.architers.context.cache.operation.PutCacheParam;
import org.redisson.api.RedissonClient;

/**
 * @author Administrator
 */
public class MapCacheOperate implements CacheOperate {

    private RedissonClient redissonClient;

    @Override
    public void put(PutCacheParam putCacheParam) {
        if (putCacheParam.isAsync()) {
            redissonClient.getMap(putCacheParam.getCacheName()).putAsync(putCacheParam.getKey(), putCacheParam.getCacheValue());
        } else {
            redissonClient.getMap(putCacheParam.getCacheName()).put(putCacheParam.getKey(), putCacheParam.getCacheValue());
        }
    }

    @Override
    public void delete(DeleteCacheParam deleteCacheParam) {
        if (deleteCacheParam.isAsync()) {
            redissonClient.getMap(deleteCacheParam.getCacheName()).removeAsync(deleteCacheParam.getKey());
        } else {
            redissonClient.getMap(deleteCacheParam.getCacheName()).removeAsync(deleteCacheParam.getKey());
        }
    }

    @Override
    public Object get(GetCacheParam getCacheParam) {
        return redissonClient.getMap(getCacheParam.getCacheName()).get(getCacheParam.getKey());
    }

    @Override
    public void deleteAll(DeleteCacheParam deleteCacheParam) {

        if (deleteCacheParam.isAsync()) {
            redissonClient.getMap(deleteCacheParam.getCacheName()).delete();

        }

        if (redissonClient.getMap(deleteCacheParam.getCacheName()).delete()) {
            throw new RuntimeException("删除缓存失败");
        }
        ;
    }
}
