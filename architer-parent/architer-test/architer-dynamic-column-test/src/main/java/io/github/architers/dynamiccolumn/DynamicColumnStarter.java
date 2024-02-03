package io.github.architers.dynamiccolumn;

import io.github.architers.model.query.ResourceDynamicColumnConfigLoad;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
