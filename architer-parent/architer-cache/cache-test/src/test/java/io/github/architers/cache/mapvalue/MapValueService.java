//package io.github.architers.cache.mapvalue;
//
//
//import io.github.architers.context.cache.CacheMode;
//import io.github.architers.cache.entity.UserInfo;
//
//import io.github.architers.context.cache.annotation.Cacheable;
//import io.github.architers.context.cache.annotation.DeleteCache;
//
//public interface MapValueService {
//
//    String cacheName="mapValue_user";
//
//    @Cacheable(cacheName =MapValueService.cacheName, key = "#userName", expireTime = 2, cacheMode = CacheMode.MAP)
//    UserInfo findByUserName(String userName);
//
//    @DeleteCache(cacheName = MapValueService.cacheName, key = "#userName",cacheMode = CacheMode.MAP)
//    void deleteByUserName(String userName);
//}
