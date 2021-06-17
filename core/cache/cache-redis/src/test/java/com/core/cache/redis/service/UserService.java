package com.core.cache.redis.service;

import com.core.cache.common.annotation.Cacheable;
import com.core.cache.common.annotation.PutCache;
import com.core.cache.common.enums.LockType;
import com.core.cache.redis.entity.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    @Cacheable(prefix = "#root.method.returnType.name", suffix = "#id", lock = LockType.reentrant, expireTime = 60)
    User findById(@NonNull Long id);

    User update(User user);

    User findByName(String name);

    void deleteByName(String name);

    /**
     * 将user作为缓存值
     *
     * @param user
     */
    @PutCache(prefix = "#user.class.name", suffix = "#user.name", cacheValue = "#user")
    void updateForCacheValue(User user);
}
