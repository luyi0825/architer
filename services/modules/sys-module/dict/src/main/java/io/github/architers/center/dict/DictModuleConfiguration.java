package io.github.architers.center.dict;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据字典模块配置类
 *
 * @author luyi
 */
@Configuration
@ComponentScan
@MapperScan("io.github.architers.center.dict.dao")
public class DictModuleConfiguration {
}
