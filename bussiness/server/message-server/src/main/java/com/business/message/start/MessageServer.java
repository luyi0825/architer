package com.business.message.start;

import com.core.boot.MoulesBuilder;
import com.core.cache.common.annotation.CustomEnableCaching;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息服务
 *
 * @author luyi
 */
@SpringBootApplication
@CustomEnableCaching
@EnableDiscoveryClient
public class MessageServer {
    public static void main(String[] args) {
        Class<?>[] modules = new MoulesBuilder().buildMoules(MessageServer.class);
        new SpringApplication(modules).run(args);
    }

}
