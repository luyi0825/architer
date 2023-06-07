package io.github.architers.redisson.cache.batchput;

import io.github.architers.context.cache.annotation.BatchPutCache;
import io.github.architers.redisson.cache.CacheUser;
import io.github.architers.cache.redisson.support.RedissonMapCacheOperate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 测试批量删除
 *
 * @author luyi
 */
@Service
public class MapBatchPutCacheService {


    @BatchPutCache(cacheName = "map:batchPutMap", cacheValue = "#cacheInfo",cacheOperate = RedissonMapCacheOperate.class)
    public void batchPutMap(Map<String, String> cacheInfo) {
        System.out.println("执行batchPutMap");
    }

    @BatchPutCache(cacheName = "map:batchPutCollection", cacheValue = "#cacheUsers",cacheOperate
            = RedissonMapCacheOperate.class,
            expireTime = 2,randomTime = 100)
    public void batchPutCollection(List<CacheUser> cacheUsers) {
        System.out.println("执行batchPutCollection");
    }
}
