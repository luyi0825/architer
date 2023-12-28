package io.github.architers.context.cache.fieldconvert.anantion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要字段转换（对对象中字段标明需要解析进行转换）
 *
 * @author luyi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedFieldConvert {


}
