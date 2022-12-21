package io.github.architers.context.cache.annotation;


import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.DefaultCacheOperate;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 向缓存中放数据
 * list放入map
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BatchPutCache {

    /**
     * @see Cacheable#cacheName()
     */
    String cacheName() default "";

    /**
     * 缓存值，支持EL表达
     * <li>当为map的时候，key|value就刚好对应</li>
     * <li>当为集合的时候，用CacheKey和CacheValue指定key|value</li>
     */
    String cacheValue() default "";

    /**
     * 缓存名称包装器
     */
    String cacheNameWrapper() default "";

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
     * 缓存管理器类型
     *
     * @see Cacheable#cacheOperate()
     */
    /**
     * 缓存操作器(对应的类执行缓存操作,类名)
     */

    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;

}
