package com.architecture.test.cache;

import com.architecture.context.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public interface CacheableUserInfoService {
    @Cacheable(cacheName = "'oneCacheable1'", key = "#userName")
    public UserInfo oneCacheable1(String userName);
}
