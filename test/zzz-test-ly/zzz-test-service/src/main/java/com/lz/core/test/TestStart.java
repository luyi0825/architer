package com.lz.core.test;



import com.lz.core.cache.common.annotation.CustomEnableCaching;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@CustomEnableCaching
@EnableRabbit
@EnableDiscoveryClient
public class TestStart {
    public static void main(String[] args) {
       // Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(TestStart.class, args);
    }
}
