package com.architecture.cache.redis.service;

import com.architecture.ultimate.module.common.cache.annotation.Cacheable;
import com.architecture.ultimate.module.common.cache.annotation.PutCache;
import com.architecture.cache.redis.entity.User;
import com.architecture.ultimate.module.common.cache.enums.LockType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    @Cacheable(cacheName = "#root.method.returnType.name", key = "#id", lockType = LockType.reentrant, expireTime = 60)
    User findById(@NonNull Long id);

    User update(User user);

    User findByName(String name);

    void deleteByName(String name);

    /**
     * 将user作为缓存值
     *
     * @param user
     */
    @PutCache(cacheName = "#user.class.name", key = "#user.name", cacheValue = "#user")
    void updateForCacheValue(User user);
}
