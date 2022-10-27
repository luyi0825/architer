package io.github.architers.context.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存注解标识
 * @author luyi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Cache {
}
