package com.architecture.test.mq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;


//@Component
//@RabbitListener(queuesToDeclare = {@Queue(name = "queue_gathering_data")})
public class Test {
    @RabbitHandler
    public void process(@Payload byte[] fileBytes, Message message, Channel channel) throws IOException, InterruptedException {

        channel.basicQos(0, 1, false);
        MessageProperties messageProperties = message.getMessageProperties();
        long time = (long) (Math.random() * 2000);
        Thread.sleep(time);
        System.out.println(new String(fileBytes) + ":" + time);
        channel.basicAck(messageProperties.getDeliveryTag(), false);

    }
}
