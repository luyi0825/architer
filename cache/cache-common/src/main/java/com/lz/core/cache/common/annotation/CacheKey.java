package com.lz.core.cache.common.annotation;

import java.lang.annotation.*;

/**
 * 标识缓存key
 *
 * @author luyi
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheKey {
}
