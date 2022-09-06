package io.github.architers.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class NextServiceApplication {
    public static void main(String[] args) {
        // Class<?>[] classes = new MoulesBuilder().buildMoules(TestStart.class);
        SpringApplication.run(NextServiceApplication.class, args);
    }
}
