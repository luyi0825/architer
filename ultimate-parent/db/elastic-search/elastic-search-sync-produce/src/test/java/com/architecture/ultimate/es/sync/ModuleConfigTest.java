package com.architecture.ultimate.es.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication(scanBasePackages = "com.architecture.ultimate.es.sync")
public class ModuleConfigTest {

    public static void main(String[] args) {
        new SpringApplication(ModuleConfigTest.class).run(args);
    }

}
