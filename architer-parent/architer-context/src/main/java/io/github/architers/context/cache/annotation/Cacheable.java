
package io.github.architers.context.cache.annotation;


import io.github.architers.context.cache.operate.*;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;


/**
 * 功能：缓存数据，缓存中有的时候，就从缓存中取值，没有就将返回结果放入缓存中并返回
 * <br>
 * ----------------------------------缓存的中key规则---------------------------
 *
 * <li> 真正的缓存key的取值：为cacheName+缓存分隔符+key的取值</li>
 * <br>
 * -----------------------------------缓存过期时间-------------------------------
 * <li>缓存过期时间：expireTime加上randomTime范围内随机生成的时间</li><br>
 *
 * @author luyi
 * @version 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Repeatable(Cacheables.class)
public @interface Cacheable {

    /**
     * 缓存名称(不支持EL表达式)
     */
    String cacheName() default "";

    /**
     * 缓存key,支持SpEL
     */
    String key();



    /**
     * 缓存随机时间,时间单位跟expireTime一致
     * ps:主要用户解决缓存雪崩，同一时刻大量缓存数据失效，大量请求到达数据库
     */
    long randomTime() default 0;

    /**
     * 缓存失效时间
     */
    long expireTime() default -1;

    /**
     * 过期时间单位
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * 条件满足的时候，进行缓存操作
     */
    String condition() default "";

    /**
     * 条件满足的时候，不进行缓存操作
     */
    String unless() default "";


}
