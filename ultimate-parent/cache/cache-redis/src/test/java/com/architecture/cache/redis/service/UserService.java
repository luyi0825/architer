package com.architecture.cache.redis.service;

import com.architecture.cache.redis.entity.User;
import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.cache.annotation.PutCache;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    @Cacheable(cacheName = "#root.method.returnType.name", key = "#id", expireTime = 60)
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
