package com.architecture.ultimate.module.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 模块标识注解，只有标识这个注解才会装配模块
 * 或者自己定义springBootStarter也可以
 * =>@Configuration+@ComponentScan会默认扫描这个类以及子包下的类
 *
 * @author luyi
 */
@Target({TYPE})
@Retention(RUNTIME)
@Documented
@Configuration
@ComponentScan
public @interface SubModule {
    /**
     * 模块名称（英文）
     */
    String name();

    /**
     * 模块名称(中文)
     */
    String caption();

    String[] basePackages() default {};

}
