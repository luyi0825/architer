package com.architecture.context.cache.operation;


import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.lock.LockEnum;
import com.architecture.context.lock.Locked;
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
     * 操作的注解
     */
    private Annotation annotation;
    /**
     * @see Cacheable#cacheName()
     */
    private String[] cacheName;
    /**
     * @see Cacheable#key()
     */
    private String key;
    /**
     * @see Cacheable#async()
     */
    private boolean async;
    /**
     * @see Cacheable#locked()
     */
    private Locked locked;

    /**
     * 条件满足的时候，进行缓存操作
     */
    private String condition;

    /**
     * 条件满足的时候，不进行缓存操作
     */
    private String unless;


}
