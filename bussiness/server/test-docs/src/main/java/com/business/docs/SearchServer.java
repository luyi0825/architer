package com.business.docs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


/**
 * 搜索服务
 *
 * @author luyi
 */
@SpringBootApplication
@RestController
public class SearchServer {
    public static void main(String[] args) {
        new SpringApplication(SearchServer.class).run(args);
    }

}
