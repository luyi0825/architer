package com.business.base;

import com.architecture.context.common.cache.annotation.EnableCustomCaching;
import com.architecture.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 模块测试
 *
 * @author luyi
 */
@SpringBootApplication
@EnableCustomCaching
@EnableDiscoveryClient
public class ModuleTest {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(ModuleTest.class);
        new SpringApplication(modules).run(args);
    }
}
