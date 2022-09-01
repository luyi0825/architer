package io.github.architers.excel.html.configuration;

import io.github.architers.excel.html.Html2XlsExport;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将html导出excel的配置类
 *
 * @author luyi
 */
@Configuration
@ImportAutoConfiguration({HtmlParserConfiguration.class})
public class Html2ExcelConfiguration {

    @Bean
    public Html2XlsExport xlsExport() {
        return new Html2XlsExport();
    }
}
