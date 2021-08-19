package com.business.file.start;

import com.architecture.starter.web.module.ModulesBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luyi
 * 文件服务启动器
 */
@SpringBootApplication
public class FileServer {
    public static void main(String[] args) {
        Class<?>[] classes = new ModulesBuilder().buildMoules(FileServer.class);
        new SpringApplication(classes).run(args);
    }
}
