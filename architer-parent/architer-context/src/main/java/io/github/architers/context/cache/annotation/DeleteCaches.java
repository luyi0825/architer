package io.github.architers.context.cache.annotation;

import java.lang.annotation.*;


/**
 * @author luyi
 * 多个删除缓存操作
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DeleteCaches {
    DeleteCache[] value();
}
