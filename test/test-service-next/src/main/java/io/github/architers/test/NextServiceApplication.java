package io.github.architers.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NextServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NextServiceApplication.class, args);
    }
}
