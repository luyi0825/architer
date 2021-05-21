package com.lz.core.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * restTemplate 配置文件
 *
 * @author luyi
 * @date 2021/5/19
 */
@Configuration
public class RestTemplateConfigure {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate= new RestTemplate();
        restTemplate.setRequestFactory();
    }



}
