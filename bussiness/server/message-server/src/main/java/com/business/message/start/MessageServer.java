package com.business.message.start;

import com.core.starter.web.module.ModulesBuilder;
import com.core.cache.common.annotation.EnableCustomCaching;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 消息服务
 *
 * @author luyi
 */
@SpringBootApplication
@EnableCustomCaching
@EnableDiscoveryClient
@MapperScan("com.business.message.*.dao")
public class MessageServer {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(MessageServer.class);
        new SpringApplication(modules).run(args);
    }

}
