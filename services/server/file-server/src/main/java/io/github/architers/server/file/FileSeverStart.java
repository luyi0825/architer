package io.github.architers.server.file;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 文件下载
 *
 * @author luyi
 */
@SpringBootApplication
@MapperScan(basePackages = "io.github.architers.server.file.dao")
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
