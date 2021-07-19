package com.core.resttemplate;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * restTemplate 配置文件
 *
 * @author luyi
 * @date 2021/5/19
 */
@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
@Import({OkHttpClientConfigure.class})
public class RestTemplateConfigure {

    public RestTemplateConfigure(){
        System.out.println("RestTemplateConfigure");
    }


    @Bean
    @ConditionalOnBean(OkHttpClient.class)
    public RestTemplate restTemplate(OkHttpClient okHttpClient) {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestFactory okHttpFactory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        restTemplate.setRequestFactory(okHttpFactory);
        return restTemplate;
    }

    /**
     * 如果没有使用连接池就初始化默认的连接池
     */
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }




}
