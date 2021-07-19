package com.core.cache.common;


import com.core.cache.common.annotation.EnableCustomCaching;
import com.core.cache.common.aspectj.AspectjConfiguration;
import com.core.cache.common.proxy.ProxyConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

import java.util.function.Predicate;


/**
 * 缓存切面导入选择器
 * <p>模仿的spring的CachingConfigurationSelector
 *
 * @author luyi
 * @see org.springframework.cache.annotation.CachingConfigurationSelector
 * </p>
 * @see EnableCustomCaching
 */
public class CacheAdviceImportSelector extends AdviceModeImportSelector<EnableCustomCaching> {

    @Override
    public String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
                return getAspectjImports();
            default:
                return null;
        }
    }

    private String[] getProxyImports() {
        return new String[]{ProxyConfiguration.class.getName(), AutoProxyRegistrar.class.getName()};
    }

    /**
     * 得到Aspectj需要导入的类
     */
    private String[] getAspectjImports() {
        return new String[]{AspectjConfiguration.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return super.getExclusionFilter();
    }
}
