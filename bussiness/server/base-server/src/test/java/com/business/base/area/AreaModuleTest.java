package com.business.base.area;

import com.architecture.ultimate.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基础数据服务
 *
 * @author luyi
 */
@SpringBootApplication
public class AreaModuleTest {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(AreaModuleTest.class);
        new SpringApplication(modules).run(args);
    }
}
