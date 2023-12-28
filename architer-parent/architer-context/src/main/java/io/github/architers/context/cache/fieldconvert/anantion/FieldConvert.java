package io.github.architers.context.cache.fieldconvert.anantion;


import io.github.architers.context.cache.enums.CacheLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据转换
 *
 * @author luyi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldConvert {


    /**
     * 转换器(找到哪个转换器类）
     */
    String converter();

    /**
     * 转换字段的数据标识（在转换器中的数据的标识）
     */
    String dataIndex() default "";

    /**
     * 通过哪个字段值进行转换
     */
    String sourceField();

    /**
     * 原始值的获取converter（为空就默认就是converter的取值）：
     * 1.当其他字段有这个converter就不会去查询数据
     * 2.当其他字段没这个converter，才会查询数据
     */
    String originValueConverter() default "";

    /**
     * originValue缓存级别,默认先使用本地，后使用远程
     * * <li>整体缓存</li>
     */
    CacheLevel cacheLevel() default CacheLevel.localAndRemote;

}
