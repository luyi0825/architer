package com.business.auth;

import com.architecture.ultimate.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthConfigurationTest {

    public static void main(String[] args) {
        Class<?>[] classes = new ModulesBuilder().buildMoules(AuthConfigurationTest.class);
        new SpringApplication(classes).run();
    }
}
