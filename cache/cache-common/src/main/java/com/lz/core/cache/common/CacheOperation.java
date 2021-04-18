package com.lz.core.cache.common;

import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.enums.KeyStrategy;
import com.lz.core.cache.common.enums.LockType;
import lombok.Data;

import java.lang.annotation.Annotation;

/**
 * @author luyi
 * 缓存操作
 * @see org.springframework.cache.interceptor.CacheOperation 参考的这个类
 */
@Data
public class CacheOperation {
    /**
     * 名称（注解的名称）
     */
    private String name;

    /**
     * 操作的注解
     */
    private Annotation annotation;

    /**
     * @see Cacheable#cacheName()
     */
    private String cacheName;

    private LockType lock;

    private String cachePrefix;
    /**
     * @see Cacheable#async()
     */
    private boolean async;

    /**
     * key的策略
     *
     * @see Cacheable#keyStrategy()
     */
    private KeyStrategy keyStrategy;


}
