package com.lz.core.testSlave;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableWebMvc
public class TestSlaveStart {
    public static void main(String[] args) {
        new SpringApplication(TestSlaveStart.class).run(args);
    }


}
