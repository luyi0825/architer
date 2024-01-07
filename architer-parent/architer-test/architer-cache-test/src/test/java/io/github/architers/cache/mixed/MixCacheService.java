package io.github.architers.cache.mixed;


import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.CachePut;

public interface MixCacheService {
    /**
     * 通过username查找
     * 1.通过用户user缓存用户信息
     * 2.缓存用户的手机号
     *
     * @param userName 用户名
     */
    @Cacheable(cacheName = "userInfo_findByUserName", key = "#userName")
    @CachePut(cacheName = "phone", key = "#userName", cacheValue = "#result.phone")
    UserInfo findByUserName(String userName);

}
