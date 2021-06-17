package com.core.cache.common.operation;

import com.core.cache.common.annotation.Cacheable;
import com.core.cache.common.enums.LockType;
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

    private String prefix;
    /**
     * @see Cacheable#async()
     */
    private boolean async;

    /**
     * @see Cacheable#suffix()
     */
    private String suffix;


}
