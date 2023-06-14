package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service("twoLevelUserInfoClusterCache")
public class TwoLevelUserInfoClusterCacheServiceImpl implements ITwoLevelUserInfoClusterCacheService {

    @Override
    public UserInfo getUserInfoWithCache(String username) {
        System.out.println("查询数据库");
        return new UserInfo().setUsername(username);
    }

    @Override
    public void delete(String username) {
        System.out.println("删除:" + username);
    }
}
