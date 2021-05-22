package com.lz.core.test.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * could alibaba 消费者的启动类
 *
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CloudAlibabaConsumer9027 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaConsumer9027.class, args);
    }
}
