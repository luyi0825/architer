package com.lz.core.cache.common.key;

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
        Class<?> annType = GenericTypeResolver.resolveTypeArgument(getClass(), CacheKeyImportSelector.class);
        Assert.state(annType != null, "Unresolvable type argument for AdviceModeImportSelector");
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(annType.getName(), false));
        if (attributes == null) {
            throw new IllegalArgumentException(String.format(
                    "@%s is not present on importing class '%s' as expected",
                    annType.getSimpleName(), importingClassMetadata.getClassName()));
        }
        KeyStrategy keyStrategy = attributes.getEnum(KEY_STRATEGY);
        String[] imports = selectKeyStrategyImports(keyStrategy);
        if (imports == null) {
            throw new IllegalArgumentException("Unknown keyStrategy: " + keyStrategy);
        }
        return imports;
    }

    private String[] selectKeyStrategyImports(KeyStrategy keyStrategy) {
        return null;
    }


    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }


}
