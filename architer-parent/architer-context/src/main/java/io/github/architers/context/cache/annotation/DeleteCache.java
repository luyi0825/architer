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
    @AliasFor("value")
    String[] cacheName() default "";

    @AliasFor("cacheName")
    String[] value() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * key的生成器
     */
    Class<? extends KeyGenerator> keyGenerator() default DefaultkeyGenerator.class;

    /**
     * 缓存值:此字段用于做批量操作（all表示删除cacheName对应的所有的缓存，其他根据对应的值删除）
     */
    String cacheValue() default "";

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
     * /**
     *
     * @see Cacheable#cacheOperate()
     */
    Class<? extends CacheOperate> cacheOperate() default DefaultCacheOperate.class;


}
