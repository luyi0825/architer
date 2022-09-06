package io.github.architers.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class UpServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpServiceApplication.class, args);
    }
}
