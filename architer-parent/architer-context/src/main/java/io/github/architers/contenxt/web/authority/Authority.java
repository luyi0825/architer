package io.github.architers.contenxt.web.authority;

import java.lang.annotation.*;

/**
 * @author luyi
 * 权限
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Authority {
    /**
     * 权限名称
     *
     * @see 1.0.0
     */
    String[] value();

}
