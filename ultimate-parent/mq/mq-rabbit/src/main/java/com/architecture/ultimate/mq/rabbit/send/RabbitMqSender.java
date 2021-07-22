package com.architecture.ultimate.mq.rabbit.send;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * rabbitMq数据发送器
 * 对数据发送进行了统一的封装，发送前进行了各种参数数据校验
 *
 * @author luyi
 */
@Component
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;

    public void send() {
        rabbitTemplate.send(null, null, null, null);
    }


    public void send(final String exchange, final String routingKey, Message message) {
        rabbitTemplate.send(null, null, null);
    }

}
