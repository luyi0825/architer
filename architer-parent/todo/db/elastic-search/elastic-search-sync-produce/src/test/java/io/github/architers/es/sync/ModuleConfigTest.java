package io.github.architers.es.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "io.github.architers.es.sync")
public class ModuleConfigTest {

    public static void main(String[] args) {
        new SpringApplication(ModuleConfigTest.class).run(args);
    }

}
