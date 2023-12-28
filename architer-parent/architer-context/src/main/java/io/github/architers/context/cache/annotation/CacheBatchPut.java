package io.github.architers.context.cache.annotation;



import io.github.architers.context.cache.enums.CacheLevel;

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
public @interface CacheBatchPut {

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
     * 缓存级别
     */
    CacheLevel cacheLevel();
}
