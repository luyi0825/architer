package io.github.architers.context.cache.annotation;

import io.github.architers.context.cache.operation.CacheOperate;
import io.github.architers.context.cache.operation.DefaultCacheOperate;
import io.github.architers.context.cache.operation.DefaultkeyGenerator;
import io.github.architers.context.cache.operation.KeyGenerator;

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


    /**
     * key的生成器
     */
    Class<? extends KeyGenerator> keyGenerator() default DefaultkeyGenerator.class;

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
     * /**
     *
     * @see Cacheable#cacheOperate()
     */
    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;
}
