package com.architecture.context.cache.annotation;


import com.architecture.context.cache.CacheConstants;
import com.architecture.context.lock.LockEnum;
import com.architecture.context.lock.Locked;

import java.lang.annotation.*;


/**
 * 缓存数据，缓存中有的时候，就从缓存中取值，没有就将换回结果放入缓存中
 * 1.所有的时间单位都是秒
 * 2.缓存的中的key的取值为：
 * *** a.如果cacheName为空就为key的取值
 * *** b.如果cacheName不为空，就为cacheName+缓存分隔符+key的取值
 *
 * @author luyi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cacheables {
    Cacheable[] value();
}
