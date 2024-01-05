package io.github.architers.context.webmvc.authority;

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
     */
    String[] value();

}
