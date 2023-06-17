package io.github.architers.gateway;

import io.github.architers.context.web.servlet.ServletExceptionHandler;
import io.github.architers.context.web.RequestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * @author luyi
 * gateWay启动器
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
    public static void main(String[] args) {
        new SpringApplication(GateWayApplication.class).run(args);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestExceptionHandler requestExceptionHandler() {
        return new ServletExceptionHandler();
    }


}
