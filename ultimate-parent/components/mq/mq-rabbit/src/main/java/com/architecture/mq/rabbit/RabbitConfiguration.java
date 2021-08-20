package com.architecture.mq.rabbit;

import com.architecture.mq.rabbit.callback.PublishCallBackForwarder;
import com.architecture.mq.rabbit.properties.RabbitmqProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * mq配置类
 */
@Configuration
@ComponentScan("com.architecture.mq.rabbit")
@EnableConfigurationProperties(value = RabbitmqProperties.class)
public class RabbitConfiguration {

    /**
     * 自定义消息转换器-Jackson
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public PublishCallBackForwarder sendConfirm(RabbitTemplate rabbitTemplate) {
        return new PublishCallBackForwarder(rabbitTemplate);
    }

}
