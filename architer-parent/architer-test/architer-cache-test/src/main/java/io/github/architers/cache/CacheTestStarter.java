package io.github.architers.cache;

import io.github.architers.context.cache.annotation.EnableCaching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;

/**
 * 缓存操作测试启动类
 *
 * @author luyi
 */
@SpringBootApplication
@EnableCaching(proxyTargetClass = true, mode = AdviceMode.PROXY)
public class CacheTestStarter {
    public static void main(String[] args) {
        SpringApplication.run(CacheTestStarter.class, args);
    }


}
