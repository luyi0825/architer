package com.lz.core.cache.common.enums;

/**
 * @author luyi
 * a_b：a表示前缀的策略，b表示后缀标识
 * 混合模式有注解就用注解，没有注解采用参数
 */
public enum KeyStrategy {
    /**
     * 都采用注解
     */
    ANNOTATIONS(),

    /**
     * 注解+参数
     */
    ANNOTATION_PARAM,

    /**
     * 注解+混合
     */
    ANNOTATION_MIX,
    /**
     * 包+注解
     */
    PACKAGE_ANNOTATION(),
    /**
     * 包+参数
     */
    PACKAGE_PARAM(),
    /**
     * 包+混合
     */
    PACKAGE_MIX()
}
