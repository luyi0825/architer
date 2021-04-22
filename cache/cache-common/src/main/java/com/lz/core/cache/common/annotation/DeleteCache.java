package com.lz.core.cache.common.annotation;


import com.lz.core.cache.common.enums.LockType;
import org.springframework.core.annotation.AliasFor;

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
    String cacheName() default "";

    /**
     * @see Cacheable#prefix()
     */
    String prefix() default "";

    /**
     * @see Cacheable#suffix()
     */
    String suffix() default "";

    /**
     * 锁的类型，比如删除锁的时候是否允许查询接口读取数据
     *
     * @see Cacheable#lock()
     */
    LockType lock() default LockType.none;

    /**
     * 是否异步
     */
    boolean async() default false;

    /**
     * true表示在方法直接删除缓存
     * false 表示在方法执行之后删除
     */
    boolean before() default false;

}
