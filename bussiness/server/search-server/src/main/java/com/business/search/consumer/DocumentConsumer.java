package com.business.search.consumer;

import com.core.es.model.EsConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author luyi
 */
@RabbitListener(queuesToDeclare = {@Queue(name = EsConstant.QUEUE_SYNC_ES_DOCUMENT)})
public class DocumentConsumer {
    @RabbitHandler
    public void handler(Message message) {
        System.out.println(message.getBody());
    }
}
