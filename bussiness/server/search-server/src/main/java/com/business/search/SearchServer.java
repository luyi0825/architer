package com.business.search;


import org.elasticsearch.common.inject.ModulesBuilder;
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
        new SpringApplication(SearchServer.class).run(args);
    }

}
