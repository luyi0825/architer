package com.business.base;

import com.architecture.ultimate.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 模块测试
 *
 * @author luyi
 */
@SpringBootApplication
public class ModuleTest {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(ModuleTest.class);
        new SpringApplication(modules).run(args);
    }
}
