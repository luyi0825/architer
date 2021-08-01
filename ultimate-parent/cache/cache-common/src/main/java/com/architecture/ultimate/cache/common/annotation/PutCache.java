package com.architecture.ultimate.cache.common.annotation;


import com.architecture.ultimate.cache.common.enums.LockType;

import java.lang.annotation.*;

/**
 * 向缓存中放数据
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PutCache {

    /**
     * @see Cacheable#cacheName()
     */
    String[] cacheName() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * @see Cacheable#randomExpireTime()
     */
    long randomExpireTime() default -1;

    /**
     * @see Cacheable#expireTime()
     */
    long expireTime() default -1;

    /**
     * @see Cacheable#lockType()
     */
    LockType lockType() default LockType.none;

    /**
     * @see Cacheable#lock()
     */
    String lock() default "";

    /**
     * @see Cacheable#async()
     */
    boolean async() default false;

    /**
     * @see Cacheable#cacheValue()
     */
    String cacheValue() default "";

    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";
}
