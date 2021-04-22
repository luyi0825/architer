package com.lz.core.cache.common.annotation;


import com.lz.core.cache.common.enums.LockType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


/**
 * 缓存数据，缓存中有的时候，就从缓存中取值，没有就将换回结果放入缓存中
 * 1.所有的时间单位都是秒
 * 2.缓存的key为缓存前缀加上缓存名称
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
     * 缓存名称
     * 1.当value不为“”，那么缓存的key就为value
     * 2.当value不为“”:缓存名称为缓存前缀+缓存分割符号+参数值
     * <p>否则，就用spElKey生成key</p>
     * 不写的话默认为缓存前缀::方法名称+（参数）
     */
    String cacheName() default "";

    /**
     * 缓存前缀
     * 默认为类的全名，其他的自己定义
     */
    String cachePrefix() default "";

    /**
     * Spring Expression Language (SpEL) expression for computing the key dynamically.
     * 缓存后缀
     * 如果为空，默认为参数值拼接
     * 如果不为空，根据spEl表达式解析值，凭借参数
     */
    String spElSuffix() default "";


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
     * 是否异步
     */
    boolean async() default false;

}
