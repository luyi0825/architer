package io.github.architers.context.lock.proxy;



import io.github.architers.context.cache.annotation.EnableCaching;
import io.github.architers.context.lock.LockAutoConfiguration;
import io.github.architers.context.lock.annotation.EnableLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

import java.util.function.Predicate;


/**
 * 锁切面导入选择器
 * <p>模仿的spring的CachingConfigurationSelector
 *
 * @author luyi
 * @see org.springframework.cache.annotation.CachingConfigurationSelector
 * </p>
 * @see EnableCaching
 */
@Slf4j
public class LockAdviceImportSelector extends AdviceModeImportSelector<EnableLock> {

    @Override
    public String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                log.info("cache use proxy adviceMode");
                return getProxyImports();
            case ASPECTJ:
                log.info("cache use aspectj adviceMode");
                return getAspectjImports();
            default:
                return null;
        }
    }

    private String[] getProxyImports() {
        return new String[]{ AutoProxyRegistrar.class.getName(),
                LockAutoConfiguration.class.getName()};
    }

    /**
     * 得到Aspectj需要导入的类
     */
    private String[] getAspectjImports() {
        return new String[]{LockAnnotationAspect.class.getName()};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return super.getExclusionFilter();
    }
}
