package com.lz.core.test.stream.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luyi
 */
@SpringBootApplication
@EnableDiscoveryClient
public class StreamRabbitProvider9020 {
    public static void main(String[] args) {
        SpringApplication.run(StreamRabbitProvider9020.class, args);
    }
}
