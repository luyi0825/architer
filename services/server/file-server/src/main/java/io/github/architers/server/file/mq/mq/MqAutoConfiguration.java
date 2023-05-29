package io.github.architers.server.file.mq.mq;

import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq自动配置类
 */
@Configuration
public class MqAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(RocketMQLocalTransactionListener.class)
    public DefaultLocalTransactionListener defaultLocalTransactionListener() {
        return new DefaultLocalTransactionListener();
    }



}
