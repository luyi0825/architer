package com.architecture.ultimate.thread.client;


import com.architecture.ultimate.thread.client.parse.ThreadPoolParser;
import com.architecture.ultimate.thread.client.properties.ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(ClientProperties.class)
public class ThreadClientConfig {

    @Bean
    public ThreadPoolParser threadPoolParser() {
        return new ThreadPoolParser();
    }
}
