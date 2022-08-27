package com.test.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luyi
 * 邮件启动类
 */
@SpringBootApplication
public class EmailApplication {
    public static void main(String[] args) {
        new SpringApplication(EmailApplication.class).run(args);
    }
}
