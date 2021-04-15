package com.lz.core.cache.common.key;

import com.lz.core.cache.common.annotation.CustomEnableCaching;
import com.lz.core.cache.common.enums.KeyStrategy;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.function.Predicate;

/**
 * @author luyi
 * 缓存key导入选择器
 * @see org.springframework.context.annotation.AdviceModeImportSelector   模仿的这个类
 */
public class CacheKeyImportSelector implements ImportSelector {

    /**
     * key生成策越
     */
    private static final String KEY_STRATEGY = "keyStrategy";

    /**
     * 选择导入
     *
     * @param importingClassMetadata 导出的注解
     * @return 需要注入的类
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Class<?> annType = CustomEnableCaching.class;
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(annType.getName(), false));
        KeyStrategy keyStrategy = attributes.getEnum(KEY_STRATEGY);
        if (keyStrategy == KeyStrategy.NONE) {
            throw new IllegalArgumentException("KeyStrategy is none");
        }
        String[] imports = selectKeyStrategyImports(keyStrategy);
        if (imports == null) {
            throw new IllegalArgumentException("Unknown keyStrategy: " + keyStrategy);
        }
        return imports;
    }

    private String[] selectKeyStrategyImports(KeyStrategy keyStrategy) {
        //选择key策略
        return new String[]{};
    }


    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }


}
