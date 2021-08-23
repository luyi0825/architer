package com.architecture.test.cache;

import com.architecture.context.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public interface CacheableService {
    @Cacheable(cacheName = "'oneCacheable'", key = "#userName")
    UserInfo oneCacheable(String userName);

    @Cacheable(cacheName = "'twoCacheable_key1'", key = "#userName")
    @Cacheable(cacheName = "'twoCacheable_key2'", key = "#userName")
    UserInfo twoCacheable(String userName);

    /**
     * 不过期
     */
    @Cacheable(cacheName = "'expireTime_1'", key = "#userName", expireTime = -1L)
    UserInfo expireTime_1(String userName);

    /**
     * 错误的过期时间
     */
    @Cacheable(cacheName = "'expireTime_2'", key = "#userName", expireTime = -3L)
    UserInfo expireTime_2(String userName);

    /**
     * 过期
     */
    @Cacheable(cacheName = "'expireTime_3'", key = "#userName", expireTime = 60)
    UserInfo expireTime_3(String userName);

    /**
     * 随机时间
     */
    @Cacheable(cacheName = "'randomTime'", key = "#userName", expireTime = 120, expireTimeUnit = TimeUnit.SECONDS, randomTime = 40)
    UserInfo randomTime(String userName);

    /**
     * userName长度大于10
     */
    @Cacheable(cacheName = "'condition'", key = "#userName", condition = "#userName.length>10")
    UserInfo condition(String userName);
}
