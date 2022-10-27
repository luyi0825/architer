package io.github.architers.context.cache.annotation;


import io.github.architers.context.cache.CacheMode;
import io.github.architers.context.cache.operation.CacheOperate;
import io.github.architers.context.cache.operation.DefaultCacheOperate;
import io.github.architers.context.cache.operation.DefaultkeyGenerator;
import io.github.architers.context.cache.operation.KeyGenerator;
import io.github.architers.context.lock.LockEnum;
import io.github.architers.context.lock.Locked;
import org.springframework.core.annotation.AliasFor;

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

    /**
     * key的生成器
     */
    Class<? extends KeyGenerator> keyGenerator() default DefaultkeyGenerator.class;


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
