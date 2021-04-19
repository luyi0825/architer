package com.lz.core.cache.common.proxy;

import com.lz.core.cache.common.CacheProcess;
import com.lz.core.cache.common.key.KeyGenerator;
import org.springframework.lang.Nullable;

/**
 * @author zhoupei
 * @create 2021/4/19
 **/
public interface CacheConfigurer {

    @Nullable
    CacheProcess cacheProcess();

    @Nullable
    KeyGenerator keyGenerator();
}
