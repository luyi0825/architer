package com.lz.core.cache.common.annotation;


import com.lz.core.cache.common.enums.KeyStrategy;
import com.lz.core.cache.common.enums.LockType;

import java.lang.annotation.*;


/**
 * 缓存数据，缓存中有的时候，就从缓存中取值，没有就将换回结果放入缓存中
 * 1.所有的时间单位都是秒
 * 2.缓存的key为缓存前缀加上缓存名称
 *
 * @author luyi
 * @date 2020/12/17
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheable {

    /**
     * 描述：缓存名称
     * ps:不写的话默认为缓存前缀::方法名称+（参数）
     */
    String cacheName() default "";

    /**
     * 缓存随机失效时间
     * ps:主要用户解决缓存雪崩，同一时刻大量缓存数据失效，大量请求到达数据库
     */
    long randomExpireTime() default -1;

    /**
     * 缓存失效时间
     * 默认30分钟
     */
    long expireTime() default 30 * 60;

    /**
     * 使用的锁
     * <li>
     * *在缓存数据的时候需要考虑到缓存穿透|雪崩|击穿|一直性的问题，
     * *我们需要到缓存的时候是否需要加锁
     * </li>
     */
    LockType lock() default LockType.none;

    /**
     * 缓存前缀
     * 默认为包名+类名+方法名称
     * <p>
     * 比如:query(String id),我们可以定义前缀为com.test,那么缓存的key就为com.test::{id的值}
     */
    String cachePrefix() default "";

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * key的策略
     */
    KeyStrategy keyStrategy() default KeyStrategy.NONE;
}
