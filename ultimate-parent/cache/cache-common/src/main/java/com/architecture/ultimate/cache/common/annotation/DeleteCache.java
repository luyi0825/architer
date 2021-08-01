package com.architecture.ultimate.cache.common.annotation;

import com.architecture.ultimate.cache.common.enums.LockType;


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
     * @see Cacheable#cacheName()
     */
    String[] cacheName() default "";

    /**
     * @see Cacheable#key()
     */
    String key();

    /**
     * @see Cacheable#lockType()
     */
    LockType lockType() default LockType.none;

    /**
     * @see Cacheable#lock()
     */
    String lock() default "";

    /**
     * @see Cacheable#async()
     */
    boolean async() default false;

    /**
     * true表示在方法直接删除缓存
     * false 表示在方法执行之后删除
     */
    boolean before() default true;

    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";

}
