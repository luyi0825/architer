package io.github.architers.context.lock.annotation;

import io.github.architers.context.cache.CacheAdviceImportSelector;
import io.github.architers.context.cache.CacheAutoConfiguration;
import io.github.architers.context.lock.LockAutoConfiguration;
import io.github.architers.context.lock.proxy.LockAdviceImportSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LockAdviceImportSelector.class})
public @interface EnableLock {
    /**
     * 指示与基于标准Java接口的代理相反，是否要创建基于子类（CGLIB）的代理。默认为false。仅当mode()设置为AdviceMode.PROXY时适用。
     */
    boolean proxyTargetClass() default false;

    /**
     * 切面模式：使用jdk代理，还是使用aspectj
     */
    AdviceMode mode() default AdviceMode.PROXY;
}
