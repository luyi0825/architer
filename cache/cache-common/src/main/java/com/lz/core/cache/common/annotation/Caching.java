package com.lz.core.cache.common.annotation;


import com.lz.core.cache.common.enums.CacheType;
import com.lz.core.cache.common.enums.Lock;

/**
 * 缓存
 * 1.所有的时间单位都是秒
 * 2.缓存的key为缓存前缀加上缓存名称
 *
 * @author luyi
 * @date 2020/12/17
 */
public @interface Caching {
    /**
     * 描述：缓存名称
     * ps:不写的话默认为缓存前缀+类型+方法名称+（参数）
     */
    String cacheName() default "";

    /**
     * 缓存类型：默认查询
     */
    CacheType cacheType() default CacheType.GET;

    /**
     * 缓存随机失效时间
     * ps:主要用户解决缓存雪崩，同一时刻大量缓存数据失效，大量请求到达数据库
     */
    long randomExpireTime();

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
    Lock lock() default Lock.none;

    /**
     * 缓存前缀
     * 默认为包名+类名
     * <p>
     * 比如:query(String id),我们可以定义前缀为com.test,那么缓存的key就为com.test::{id的值}
     */
    String cachePrefix() default "";


}
