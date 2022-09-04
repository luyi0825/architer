package io.github.architers.context.module;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * @author luyi
 * 子模块
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan
@Configuration
public @interface SubModule {
    String name();

    String caption();

    String[] basePackages() default {};


}
