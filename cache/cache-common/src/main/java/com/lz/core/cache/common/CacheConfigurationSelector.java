package com.lz.core.cache.common;


import com.lz.core.cache.common.annotation.CustomEnableCaching;
import com.lz.core.cache.common.aspectj.AspectjConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

import java.util.function.Predicate;


/**
 * 缓存配置选择器
 * <p>模仿的spring中的缓存配置解析器
 *
 * @author luyi
 * @see org.springframework.cache.annotation.CachingConfigurationSelector
 * </p>
 * @see com.lz.core.cache.common.annotation.CustomEnableCaching
 */
public class CacheConfigurationSelector extends AdviceModeImportSelector<CustomEnableCaching> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
                return getAspectJImports();
            default:
                return null;
        }
    }

    private String[] getProxyImports() {
        return null;
    }

    private String[] getAspectJImports() {
        return new String[]{CacheConfiguration.class.getName(), AspectjConfiguration.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return null;
    }
}
