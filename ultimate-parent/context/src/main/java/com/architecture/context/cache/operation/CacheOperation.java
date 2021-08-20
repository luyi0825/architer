package com.architecture.context.cache.operation;



import com.architecture.context.cache.annotation.Cacheable;
import com.architecture.context.lock.LockType;
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
     * @see Cacheable#lockType()
     */
    private LockType lockType;
    /**
     * @see Cacheable#lock()
     */
    private String lock;
    /**
     * @see Cacheable#key()
     */
    private String key;
    /**
     * @see Cacheable#async()
     */
    private boolean async;


}
