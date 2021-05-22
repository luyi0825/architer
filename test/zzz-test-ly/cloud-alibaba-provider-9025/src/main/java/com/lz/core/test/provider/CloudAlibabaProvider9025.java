package com.lz.core.test.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * springCloudAlibaba 提供者启动类
 *
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaProvider9025 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaProvider9025.class, args);
    }
}
