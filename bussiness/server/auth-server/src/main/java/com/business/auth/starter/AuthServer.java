package com.business.auth.starter;

import com.core.starter.web.module.MoulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luyi
 * auth服务启动器
 */
@SpringBootApplication
public class AuthServer {
    public static void main(String[] args) {
        Class<?>[] classes = new MoulesBuilder().buildMoules(AuthServer.class);
        new SpringApplication(classes).run(args);
    }
}
