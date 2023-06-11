package io.github.architers.context.cache.annotation;

import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.DefaultCacheOperate;

/**
 * 删除整个缓存
 *
 * @author luyi
 */
public @interface DeleteAllCache {
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

}
