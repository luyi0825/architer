package com.lz.core.excel.html.configuration;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 将html导出excel的配置类
 * @author luyi
 */
@Configuration
@ImportAutoConfiguration({HtmlParserConfiguration.class})
public class Html2ExcelConfiguration {
}
