package io.github.architers.context.cache.annotation;


import io.github.architers.context.cache.operation.CacheOperate;
import io.github.architers.context.cache.operation.DefaultCacheOperate;
import io.github.architers.context.cache.operation.DefaultkeyGenerator;
import io.github.architers.context.cache.operation.KeyGenerator;

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
     */
    String cacheValue() default "";


    /**
     * key的生成器
     */
    Class<? extends KeyGenerator> keyGenerator() default DefaultkeyGenerator.class;


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
    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;

}
