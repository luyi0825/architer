package com.architecture.ultimate.cache.common.annotation;


import com.architecture.ultimate.cache.common.Constants;
import com.architecture.ultimate.cache.common.enums.LockType;

import java.lang.annotation.*;


/**
 * 缓存数据，缓存中有的时候，就从缓存中取值，没有就将换回结果放入缓存中
 * 1.所有的时间单位都是秒
 * 2.缓存的中的key的取值为：
 * *** a.如果cacheName为空就为key的取值
 * *** b.如果cacheName不为空，就为cacheName+缓存分隔符+key的取值
 *
 * @author luyi
 * @date 2020/12/17
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheable {

    /**
     * 缓存名称,
     * <li>解析后会缓存起来，下次直接取值</li>
     */
    String[] cacheName() default "";

    /**
     * 缓存key,支持SpEL
     */
    String key();

    /**
     * 缓存随机失效时间
     * ps:主要用户解决缓存雪崩，同一时刻大量缓存数据失效，大量请求到达数据库
     */
    long randomExpireTime() default -1;

    /**
     * 缓存失效时间
     * -1标识永不失效
     */
    long expireTime() default Constants.DEFAULT_CACHE_EXPIRE_TIME;

    /**
     * 使用的锁
     * <li>
     * *在缓存数据的时候需要考虑到缓存穿透|雪崩|击穿|一直性的问题，
     * *我们需要到缓存的时候是否需要加锁
     * </li>
     */
    LockType lockType() default LockType.none;

    /**
     * 锁的值
     * <li>当lockType不为node，默认值为类名（例如com.architecture.ultimate.xxx）</li>
     * <li>支持EL表达式</li>
     */
    String lock() default "";


    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * 缓存值,默认为方法返回值，支持spEL表达式
     */
    String cacheValue() default "";

    /**
     * 条件满足的时候，进行缓存操作
     */
    String condition() default "";

    /**
     * 条件满足的时候，不进行缓存操作
     */
    String unless() default "";

}
