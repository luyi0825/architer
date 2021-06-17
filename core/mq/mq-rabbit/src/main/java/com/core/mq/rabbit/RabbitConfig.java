package com.core.mq.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * mq配置类
 */
@Configuration
public class RabbitConfig {


    private RabbitTemplate rabbitTemplate;

    /**
     * 自定义消息转换器-Jackson
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Autowired(required = false)
    @Bean
    public PublishCallBackForwarder sendConfirm(RabbitTemplate rabbitTemplate){
       return new PublishCallBackForwarder(rabbitTemplate);
    }



}
