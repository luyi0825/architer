package io.github.arthiters.test;

import io.github.architers.server.file.FileTaskTemplate;
import io.github.architers.server.file.RocketMqExportStatusReport;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestApp {
    public static void main(String[] args) {
        new SpringApplication(TestApp.class).run(args);
    }

    @Bean
    public RocketMqExportStatusReport rocketMqExportStatusReport(RocketMQTemplate rocketMQTemplate) {
        return new RocketMqExportStatusReport(rocketMQTemplate);
    }

    @Bean
    public FileTaskTemplate fileTaskTemplate() {
        return new FileTaskTemplate();
    }
}
