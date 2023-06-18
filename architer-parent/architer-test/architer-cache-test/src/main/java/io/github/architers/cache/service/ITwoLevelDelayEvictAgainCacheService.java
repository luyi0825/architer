package io.github.architers.cache.service;


import io.github.architers.cache.entity.UserInfo;

public interface ITwoLevelDelayEvictAgainCacheService {

    /**
     * 获取用户信息
     */

    UserInfo getUserInfoWithCache(String username);

    /**
     * 获取用户信息
     */

    UserInfo getUserInfoOnlyCache(String username);


    void delete(String username);

    void updateUserInfo(UserInfo userInfo);

    void updateUserInfoWithEvict(UserInfo userInfo);
}
