package com.core.test.provider.config;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luyi
 */
@ConfigurationProperties(prefix = "user")
@Data
public class UserConfig {
    private String username = "test";

//    @Override
//    public void destroy() throws Exception {
//        System.out.println("destroy");
//    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet");
//    }
}
