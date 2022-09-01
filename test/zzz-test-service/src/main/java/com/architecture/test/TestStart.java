package io.github.architers.test;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableCaching
@EnableRabbit
@EnableDiscoveryClient
public class TestStart {
    public static void main(String[] args) {
        // Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(TestStart.class, args);
    }
}
