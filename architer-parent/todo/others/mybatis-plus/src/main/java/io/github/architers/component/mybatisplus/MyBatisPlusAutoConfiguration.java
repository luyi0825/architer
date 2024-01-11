package io.github.architers.component.mybatisplus;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MyBatisPlusAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusAbstactMethodLoader mybatisPlusAbstactMethodLoader() {
        return new DefaultMybatisPlusAbstactMethodLoader();
    }


    @Bean
    public CustomizedSqlInjector customizedSqlInjector(MybatisPlusAbstactMethodLoader loader) {
        return new CustomizedSqlInjector(loader);
    }

}
