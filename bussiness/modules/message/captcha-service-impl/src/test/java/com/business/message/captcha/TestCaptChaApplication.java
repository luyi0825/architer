package com.business.message.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TestCaptChaApplication {

    public static void main(String[] args) {
        new SpringApplication().run(TestCaptChaApplication.class,args);
    }

    @Bean
    @ConditionalOnBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
