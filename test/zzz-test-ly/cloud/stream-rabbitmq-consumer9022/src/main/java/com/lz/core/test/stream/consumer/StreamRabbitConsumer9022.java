package com.lz.core.test.stream.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StreamRabbitConsumer9022 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitConsumer9022.class, args);
    }
}
