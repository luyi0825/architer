package io.github.architers.context.cache.annotation;

import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.DefaultCacheOperate;

import java.lang.annotation.*;

/**
 * 批量删除缓存
 *
 * @author luyi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BatchDeleteCache {
    /**
     * 缓存名称(不支持EL表达式)
     */
    String cacheName();

    /**
     * 批量删除的key,当key为空，就删除所有的
     */
    String keys();


    String cacheNameWrapper() default "";

    /**
     * 是否异步删除
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
     *
     * @see Cacheable#cacheOperate()
     */
    /**
     * 缓存操作器
     */
    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;

}
