package com.core.test.bus.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 * 消息总线客户端启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BusClientStart9018 {
    public static void main(String[] args) {
        SpringApplication.run(BusClientStart9018.class, args);
    }
}
