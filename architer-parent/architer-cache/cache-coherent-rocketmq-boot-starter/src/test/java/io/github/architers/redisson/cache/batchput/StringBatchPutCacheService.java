package io.github.architers.redisson.cache.batchput;

import io.github.architers.context.cache.annotation.BatchPutCache;

import io.github.architers.redisson.cache.CacheUser;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * 测试批量删除
 *
 * @author luyi
 */
@Service
public class StringBatchPutCacheService {


    @BatchPutCache(cacheName = "string:batchPutMap", cacheValue = "#cacheInfo")
    public void batchPutMap(Map<String, String> cacheInfo) {
        System.out.println("执行batchPutMap");
    }

    @BatchPutCache(cacheName = "string:batchPutCollection", cacheValue = "#cacheUsers",
            expireTime = 2,randomTime = 100)
    public void batchPutCollection(List<CacheUser> cacheUsers) {
        System.out.println("执行batchPutCollection");
    }
}
