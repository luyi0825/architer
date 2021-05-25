package com.lz.core.test.provider;

import com.lz.core.test.provider.config.UserConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * springCloudAlibaba 提供者启动类
 *
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(UserConfig.class)
@RestController
public class CloudAlibabaProvider9025 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaProvider9025.class, args);
    }

    @RequestMapping("/test")
    public String test() {
        return UUID.randomUUID().toString();
    }
}
