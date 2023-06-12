package io.github.architers.context.cache.annotation;


import java.lang.annotation.*;

/**
 * 向缓存中放数据，支持多个PutCache
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CachePuts {

    CachePut[] value();
}
