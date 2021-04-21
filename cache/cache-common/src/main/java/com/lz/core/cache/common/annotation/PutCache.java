package com.lz.core.cache.common.annotation;

import com.lz.core.cache.common.enums.LockType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PutCache {

    /**
     * @see Cacheable#value()
     */
    @AliasFor("cacheNames")
    String value() default "";

    /**
     * @see Cacheable#cachePrefix()
     */
    String cachePrefix() default "";

    /**
     * @see Cacheable#spElKey()
     */
    String spElKey() default "";

    /**
     * @see Cacheable#randomExpireTime()
     */
    long randomExpireTime() default -1;

    /**
     * @see Cacheable#expireTime()
     */
    long expireTime() default 30 * 60;

    /**
     * @see Cacheable#lock()
     */
    LockType lock() default LockType.none;

    /**
     * @see Cacheable#async()
     */
    boolean async() default false;

}
