package io.github.architers.context.cache.annotation;


import java.lang.annotation.*;

/**
 * @author luyi
 * 缓存key的标识
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheKey {

    /**
     * 排序,越小越在前边
     */
    int order() default 0;

}
