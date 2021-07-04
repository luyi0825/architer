package com.business.search.start;

import com.core.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 搜索服务
 *
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MessageServer {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(MessageServer.class);
        new SpringApplication(modules).run(args);
    }

}
