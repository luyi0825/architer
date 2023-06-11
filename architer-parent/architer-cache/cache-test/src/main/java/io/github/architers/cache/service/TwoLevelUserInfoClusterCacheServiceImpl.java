package io.github.architers.cache.service;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.BatchPutCache;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.DeleteCache;
import io.github.architers.context.cache.annotation.PutCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("twoLevelUserInfoClusterCache")
public class TwoLevelUserInfoClusterCacheServiceImpl implements ITwoLevelUserInfoClusterCacheService {

    @Override
    public UserInfo getUserInfoWithCache(String username) {
        return new UserInfo().setUsername(username);
    }

    @Override
    public void delete(String username) {
        System.out.println("删除:" + username);
    }
}
