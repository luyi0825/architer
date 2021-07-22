package com.business.search;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 搜索服务
 *
 * @author luyi
 */
@SpringBootApplication
public class SearchServer {
    public static void main(String[] args) {
        new SpringApplication(SearchServer.class).run(args);
    }

}
