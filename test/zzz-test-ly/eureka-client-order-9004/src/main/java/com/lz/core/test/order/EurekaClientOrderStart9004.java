package com.lz.core.test.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author luyi
 * <p>
 * Eureka客户端
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class},scanBasePackages = "com.ly.core.test.order")
@EnableEurekaClient
public class EurekaClientOrderStart9004 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientOrderStart9004.class, args);
    }
}
