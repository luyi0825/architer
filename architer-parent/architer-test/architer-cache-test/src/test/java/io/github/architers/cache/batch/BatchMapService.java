//package io.github.architers.cache.batch;
//
//import io.github.architers.context.cache.CacheConstants;
//import io.github.architers.cache.entity.UserInfo;
//
//import io.github.architers.context.cache.annotation.CacheEvict;
//import io.github.architers.context.cache.annotation.PutCache;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class BatchMapService {
//
//    @PutCache(cacheName = "BatchMapService_batchMap", key = CacheConstants.BATCH_CACHE_KEY, expireTime = 30, cacheValue = "#userMap")
//    public void batchPutMap(Map<String, UserInfo> userMap) {
//
//    }
//
//    @PutCache(cacheName = "BatchMapService_batchCollection", key = CacheConstants.BATCH_CACHE_KEY, expireTime = 30, cacheValue = "#userInfos")
//    public void batchPutCollection(List<UserInfo> userInfos) {
//    }
//
//
//}
