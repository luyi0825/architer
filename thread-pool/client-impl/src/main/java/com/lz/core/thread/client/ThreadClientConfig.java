package com.lz.core.thread.client;

import com.lz.thread.client.parse.ThreadPoolParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration
public class ThreadClientConfig {

    @Bean
    public ThreadPoolParser threadPoolParser() {
        return new ThreadPoolParser();
    }
}
