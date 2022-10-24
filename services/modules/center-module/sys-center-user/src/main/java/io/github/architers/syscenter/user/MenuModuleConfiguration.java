package io.github.architers.syscenter.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据字典模块配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan
@MapperScan("io.github.architers.syscenter.user.dao")
public class MenuModuleConfiguration {


}
