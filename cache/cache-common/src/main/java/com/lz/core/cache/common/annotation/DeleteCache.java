package com.lz.core.cache.common.annotation;


import com.lz.core.cache.common.enums.KeyStrategy;
import com.lz.core.cache.common.enums.Lock;

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
public @interface DeleteCache {
    /**
     * 缓存名称
     *
     * @see Cacheable#cacheName()
     */
    String cacheName() default "";

    /**
     * @see Cacheable#cachePrefix()
     */
    String cachePrefix() default "";

    /**
     * 锁的类型，比如删除锁的时候是否允许查询接口读取数据
     *
     * @see Cacheable#lock()
     */
    Lock lock() default Lock.none;

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * key的策略
     */
    KeyStrategy keyStrategy() default KeyStrategy.NONE;


}
