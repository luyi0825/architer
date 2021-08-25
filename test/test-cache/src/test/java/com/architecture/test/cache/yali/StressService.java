package com.architecture.test.cache.yali;

import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.test.cache.UserInfo;

public interface StressService {

    UserInfo findByNameDirect(String name);

    @Cacheable(cacheName = "'findByName'", key = "#name")
    UserInfo findByNameAnnotation(String name);
}
