package io.github.architers.context.cache.annotation;

/**
 * 删除整个缓存
 *
 * @author luyi
 */
public @interface CacheEvictAll {
    /**
     * 缓存名称(不支持EL表达式)
     */
    String[] cacheName() default "";

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
     * true表示在方法调用删除缓存
     * false 表示在方法执行之后删除
     */
    boolean beforeInvocation() default true;

}
