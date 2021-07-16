package com.business.search;

import com.core.starter.web.module.ModulesBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 搜索服务
 *
 * @author luyi
 */
@SpringBootApplication
@EnableRabbit
//@EnableDiscoveryClient
public class SearchServer {
    public static void main(String[] args) {
        Class<?>[] modules = new ModulesBuilder().buildMoules(SearchServer.class);
        new SpringApplication(modules).run(args);
    }

}
