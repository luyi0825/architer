package com.lz.core.cache.common.annotation;

import com.lz.core.cache.common.CacheAdviceImportSelector;
import com.lz.core.cache.common.CacheConfiguration;
import com.lz.core.cache.common.key.CacheKeyImportSelector;
import com.lz.core.cache.common.enums.KeyStrategy;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author luyi
 * 自定义开启缓存
 * @see org.springframework.cache.annotation.EnableCaching  对于proxyTargetClass|mode看这个类;
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CacheAdviceImportSelector.class, CacheKeyImportSelector.class, CacheConfiguration.class})
public @interface CustomEnableCaching {
    /**
     * 指示与基于标准Java接口的代理相反，是否要创建基于子类（CGLIB）的代理。默认为false。仅当mode()设置为AdviceMode.PROXY时适用。
     */
    boolean proxyTargetClass() default false;

    /**
     * 切面模式：使用jdk代理，还是使用aspectj
     */
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * key 生成策略
     */
    KeyStrategy keyStrategy() default KeyStrategy.PARAM;
}
