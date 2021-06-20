package com.bussiness;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ly
 * gateway网关
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class GatewayStart {
    public static void main(String[] args) {
        SpringApplication.run(GatewayStart.class, args);
    }
}
