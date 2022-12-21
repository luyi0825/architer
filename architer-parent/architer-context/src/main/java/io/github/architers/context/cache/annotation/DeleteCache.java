package io.github.architers.context.cache.annotation;


import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.DefaultCacheOperate;

import java.lang.annotation.*;

/**
 * 删除缓存
 *
 * @author luyi
 * @date 2020/12/26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(DeleteCaches.class)
public @interface DeleteCache {

    /**
     * @see Cacheable#cacheName()
     */
    String cacheName() default "";


    String key() default "";

    String cacheNameWrapper() default "";
    /**
     * 是否异步删除
     */
    boolean async() default false;

    /**
     * true表示在方法直接删除缓存
     * false 表示在方法执行之后删除
     */
    boolean before() default true;

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

    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;
}
