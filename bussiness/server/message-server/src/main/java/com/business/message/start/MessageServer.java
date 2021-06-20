package com.business.message.start;

import com.core.boot.MoulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消息服务
 *
 * @author luyi
 */
@SpringBootApplication
public class MessageServer {

    public static void main(String[] args) {
        Class<?>[] modules = new MoulesBuilder().buildMoules(MessageServer.class);
        new SpringApplication(modules).run(args);
    }

}
