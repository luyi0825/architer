package com.core.druid;


import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 阿里Druid配置
 */
@Configuration
public class DruidConfig {

    public DruidConfig(){
        System.out.println("init DruidConfig");
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DruidDataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDefaultAutoCommit(false);
        //配置公用监控数据
        druidDataSource.setUseGlobalDataSourceStat(true);
        return druidDataSource;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(500);
        statFilter.setLogSlowSql(true);
        return statFilter;
    }
}
