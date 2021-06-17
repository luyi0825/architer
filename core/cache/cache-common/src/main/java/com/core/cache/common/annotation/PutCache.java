package com.core.cache.common.annotation;

import com.core.cache.common.enums.LockType;

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
    String cacheName() default "";

    /**
     * @see Cacheable#prefix()
     */
    String prefix() default "";

    /**
     * @see Cacheable#suffix()
     */
    String suffix() default "";

    /**
     * @see Cacheable#randomExpireTime()
     */
    long randomExpireTime() default -1;

    /**
     * @see Cacheable#expireTime()
     */
    long expireTime() default 30 * 60;

    /**
     * @see Cacheable#lock()
     */
    LockType lock() default LockType.none;

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
