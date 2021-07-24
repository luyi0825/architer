package com.business.search;


import com.architecture.ultimate.utils.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @PostMapping("/callback")
    public void callBack(@RequestBody Map<String, Object> params) {
        System.out.println(JsonUtils.toJsonString(params));
    }
}
