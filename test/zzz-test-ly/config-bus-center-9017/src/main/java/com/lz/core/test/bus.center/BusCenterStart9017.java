package com.lz.core.test.bus.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息总线center启动器
 *
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BusCenterStart9017 {
    public static void main(String[] args) {
        SpringApplication.run(BusCenterStart9017.class, args);
    }
}
