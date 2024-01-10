package io.github.architers.context.sql;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sql任务执行自动配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class SqlTaskExecutorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SqlTaskExecutor sqlTaskExecutor() {
        return new SqlTaskExecutor();
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlTaskUtils sqlTaskUtils() {
        return new SqlTaskUtils();
    }
}
