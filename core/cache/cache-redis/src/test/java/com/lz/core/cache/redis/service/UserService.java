package com.lz.core.cache.redis.service;

import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.enums.LockType;
import com.lz.core.cache.redis.entity.User;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    @Cacheable(prefix = "#root.method.returnType.name", suffix = "#id", lock = LockType.reentrant, expireTime = 60)
    User findById(@NonNull Long id);

    User update(User user);

    User findByName(String name);

    void deleteByName(String name);
}
