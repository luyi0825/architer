package com.lz.core.excel.html.configuration;

import com.lz.core.excel.html.parser.DefaultHtmlParser;
import com.lz.core.excel.html.parser.HtmlParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * html解析配置类
 */
@Configuration
public class HtmlParserConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HtmlParser htmlParser() {
        return new DefaultHtmlParser(null);
    }
}
