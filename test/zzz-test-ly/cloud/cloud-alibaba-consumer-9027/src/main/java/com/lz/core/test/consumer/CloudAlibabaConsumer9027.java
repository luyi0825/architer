package com.lz.core.test.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * could alibaba 消费者的启动类
 *
 * @author luyi
 */
@SpringBootApplication(scanBasePackages="com.lz.core.test.consumer.zipkin.controller")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lz.core.test.consumer.zipkin.feign"})
@RestController
public class CloudAlibabaConsumer9027 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaConsumer9027.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        return UUID.randomUUID().toString();
    }
}
