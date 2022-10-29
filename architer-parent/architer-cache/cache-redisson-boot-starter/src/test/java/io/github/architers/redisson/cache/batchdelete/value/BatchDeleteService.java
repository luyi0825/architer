package io.github.architers.redisson.cache.batchdelete.value;

import io.github.architers.context.cache.annotation.BatchDeleteCache;
import io.github.architers.redisson.cache.CacheUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BatchDeleteService {


    @BatchDeleteCache(cacheName = "batchDeleteMap", keys = "#map")
    public void batchDeleteMap(Map<String, String> map) {
        System.out.println("执行batchDeleteMap");
    }

    @BatchDeleteCache(cacheName = "batchDeleteCollection", keys = "#cacheUsers")
    public void batchDeleteCollection(List<CacheUser> cacheUsers) {
        System.out.println("执行batchDeleteCollection");
    }

}
