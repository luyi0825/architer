package com.business.base.start;

import com.architecture.ultimate.starter.web.module.ModulesBuilder;
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
@EnableDiscoveryClient
@MapperScan("com.business.base.*.dao")
public class BaseServer {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(BaseServer.class);
        new SpringApplication(modules).run(args);
    }

}
