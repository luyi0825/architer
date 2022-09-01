
package io.github.architers.mybatisplus;

import io.github.architers.mybatisplus.builder.QueryWrapperBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置
 *
 * @author luyi
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnMissingBean
    public MybatisPlusInterceptor paginationInterceptor() {
        return new  MybatisPlusInterceptor();
    }

    @Bean
    public QueryWrapperBuilder<?> queryWrapperBuilder(){
        return new QueryWrapperBuilder<>();
    }
}
