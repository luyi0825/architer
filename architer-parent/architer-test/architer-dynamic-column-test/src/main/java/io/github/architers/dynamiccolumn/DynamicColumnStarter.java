package io.github.architers.dynamiccolumn;

import io.github.architers.query.dynimic.ResourceDynamicColumnConfigLoad;
import io.github.architers.query.dynimic.mybatis.DynamicInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("io.github.architers.dynamiccolumn.mapper")
public class DynamicColumnStarter implements CommandLineRunner {
    public static void main(String[] args) {
        new SpringApplication(DynamicColumnStarter.class).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        ResourceDynamicColumnConfigLoad resourceDynamicColumnConfigLoad = new ResourceDynamicColumnConfigLoad();
        resourceDynamicColumnConfigLoad.load();
    }

    @Bean
    public DynamicInterceptor dynamicInterceptor() {
        return new DynamicInterceptor();
    }
}
