package io.github.architers.cache.delete;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.CacheEvict;

public interface DeleteService {
    @CacheEvict(cacheName = "oneDelete", key = "#userInfo.username")
    void oneDelete(UserInfo userInfo);
}
