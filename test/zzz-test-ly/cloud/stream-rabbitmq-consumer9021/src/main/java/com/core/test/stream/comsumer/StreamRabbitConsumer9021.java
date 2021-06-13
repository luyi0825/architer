package com.core.test.stream.comsumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StreamRabbitConsumer9021 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitConsumer9021.class, args);
    }
}
