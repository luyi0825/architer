package io.github.architers.context.cache.annotation;

import java.lang.annotation.*;

/**
 * 批量删除缓存
 *
 * @author luyi
 * @since 1.0.1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheBatchEvict {
    /**
     * 缓存名称(不支持EL表达式)
     */
    String cacheName();

    /**
     * 批量删除的key,当key为空，就删除所有的
     */
    String keys();

    /**
     * key是否需要解析:
     * false就代表keys为集合，集合中值就为缓存key<br>
     * true 当keys为map的时候,map#keySet就是还存key，当为集合的时候，标准@CacheKey的字段就是缓存key
     */
    boolean parseKeys() default true;

    /**
     * true表示在方法调用删除缓存
     * false 表示在方法执行之后删除
     */
    boolean beforeInvocation() default true;

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


}
