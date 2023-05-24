package io.github.architers.server.file;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * 文件服务
 *
 * @author luyi
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.architers.server.file.mapper")
@EnableAsync
@EnableDubbo(scanBasePackages = "io.github.architers.server.file.dubbo")
public class FileSeverStart {

    public static void main(String[] args) {
        new SpringApplication(FileSeverStart.class).run(args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
