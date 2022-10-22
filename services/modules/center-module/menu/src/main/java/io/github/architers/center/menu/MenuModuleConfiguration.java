package io.github.architers.center.menu;

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
@MapperScan("io.github.architers.center.menu.dao")
public class MenuModuleConfiguration {


}
