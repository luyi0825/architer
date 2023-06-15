package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.CacheEvict;
import io.github.architers.context.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author luyi
 */
@Service("twoLevelUserInfoClusterCache")
public class TwoLevelUserInfoClusterCacheServiceImpl implements ITwoLevelUserInfoClusterCacheService {

    @Override
    @Cacheable(cacheName = "twoLevelCluster", key = "#username")
    public UserInfo getUserInfoWithCache(String username) {
        System.out.println("查询数据库");
        return new UserInfo().setUsername(username);
    }

    @Override
    @CacheEvict(cacheName = "twoLevelCluster",key = "#username")
    public void delete(String username) {
        System.out.println("删除:" + username);
    }
}
