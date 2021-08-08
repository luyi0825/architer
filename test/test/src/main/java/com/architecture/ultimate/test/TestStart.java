package com.architecture.ultimate.test;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackages = "com.architecture.ultimate.test.feign")
public class TestStart {

    public static void main(String[] args) {
        SpringApplication.run(TestStart.class, args);
    }

}
