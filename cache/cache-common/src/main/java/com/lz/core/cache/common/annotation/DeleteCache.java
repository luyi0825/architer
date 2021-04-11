package com.lz.core.cache.common.annotation;

import com.lz.core.cache.common.Lock;

import java.lang.annotation.Documented;

/**
 * 删除缓存
 * 删除的缓存名称默认为缓存的前缀加上缓存名称
 *
 * @author luyi
 * @date 2020/12/26
 */
@Documented

public @interface DeleteCache {
    /**
     * 缓存名称
     *
     * @see Caching#cacheName()
     */
    String cacheName() default "";

    /**
     * 锁的类型，比如删除锁的时候是否允许查询接口读取数据
     *
     * @see Caching#lock()
     */
    Lock lock() default Lock.none;

    /**
     * @see Caching#cachePrefix()
     */
    String cachePrefix() default "";

}
