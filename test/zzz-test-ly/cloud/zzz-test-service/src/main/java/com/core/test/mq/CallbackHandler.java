package com.core.test.mq;

import com.core.mq.rabbit.ConfirmCallbackHandler;
import com.core.mq.rabbit.ReturnCallbackHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 */
@Component
public class CallbackHandler implements ConfirmCallbackHandler, ReturnCallbackHandler {


    @Override
    public String getConfirmCallbackKey() {
        return CallbackHandler.class.getName();
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("in confirm");
    }

    @Override
    public String getReturnCallbackKey() {
        return CallbackHandler.class.getName();
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("in return");
    }
}
