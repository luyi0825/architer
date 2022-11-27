package io.github.architers.context.web;

import java.lang.annotation.*;

/**
 * 忽略ResponseResult，标识该注解，不会将返回结果转换为ResponseResult
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreResponseResult {
}
