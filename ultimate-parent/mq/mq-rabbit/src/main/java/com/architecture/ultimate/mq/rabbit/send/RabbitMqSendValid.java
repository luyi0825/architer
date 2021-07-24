package com.architecture.ultimate.mq.rabbit.send;

import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 * rabbitMq发送校验器
 * 针对一些消息头，以及数据标识进行判断
 */
@Component
public class RabbitMqSendValid {

    public void valid(Message message) {
        // message.getMessageProperties().set
    }

}
