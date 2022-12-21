package io.github.architers.context.cache.annotation;


import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.DefaultCacheOperate;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 向缓存中放数据
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(PutCaches.class)
public @interface PutCache {

    /**
     * @see Cacheable#cacheName()
     */
    String cacheName() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    String cacheNameWrapper() default "";

    /**
     * 缓存值，支持EL表达式
     */
    String cacheValue();


    /**
     * @see Cacheable#randomTime()
     */
    long randomTime() default 0L;


    /**
     * @see Cacheable#expireTime()
     */
    long expireTime() default -1;

    /**
     * @see Cacheable#timeUnit()
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 异步操作
     */
    boolean async() default false;

    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";

    /**
     * 缓存操作器
     *
     * @see Cacheable#cacheOperate()
     */
    /**
     * 缓存操作器
     */
    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;

}
