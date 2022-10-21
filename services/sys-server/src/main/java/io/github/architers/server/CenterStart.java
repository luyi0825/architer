package io.github.architers.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 中台启动类
 *
 * @author luyi
 */
@SpringBootApplication
public class CenterStart {

    public static void main(String[] args) {
        new SpringApplication(CenterStart.class).run(args);
    }

}
