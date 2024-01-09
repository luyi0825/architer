package io.github.architers.test.lock;


import io.github.architers.context.lock.annotation.EnableLock;
import io.github.architers.context.lock.support.zookpeer.ZkLockAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLock
@ImportAutoConfiguration(classes = ZkLockAutoConfiguration.class)
public class LockTestApplication {
    public static void main(String[] args) {
        new SpringApplication(LockTestApplication.class).run(args);
    }
}
