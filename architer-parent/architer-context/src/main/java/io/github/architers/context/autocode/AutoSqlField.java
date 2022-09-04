package io.github.architers.context.autocode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author luyi
 * 自动生成字段
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface AutoSqlField {

    /**
     * 否表示下划线，是表示驼峰
     */
    boolean hump() default false;
}
