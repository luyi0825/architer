package com.business.base.start;

import com.architecture.context.common.cache.annotation.EnableCustomCaching;
import com.architecture.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 基础数据服务
 *
 * @author luyi
 */
@SpringBootApplication
@EnableCustomCaching
@EnableDiscoveryClient
public class BaseServer {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(BaseServer.class);
        new SpringApplication(modules).run(args);
    }
}
