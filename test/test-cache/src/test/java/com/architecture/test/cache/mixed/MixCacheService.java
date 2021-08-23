package com.architecture.test.cache.mixed;

import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.cache.annotation.Cacheables;
import com.architecture.context.cache.annotation.PutCache;
import com.architecture.test.cache.UserInfo;

public interface MixCacheService {
    /**
     * 通过username查找
     * 1.通过用户user缓存用户信息
     * 2.缓存用户的手机号
     *
     * @param userName 用户名
     */
    @Cacheable(cacheName = "'userInfo_findByUserName'", key = "#userName")
    @PutCache(cacheName = "'phone'", key = "#userName", cacheValue = "#result.phone")
    UserInfo findByUserName(String userName);

}
