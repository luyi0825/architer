package com.architecture.ultimate.test.mq;


import com.architecture.ultimate.mq.rabbit.callback.ConfirmCallbackHandler;
import com.architecture.ultimate.mq.rabbit.callback.ReturnCallbackHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 */
@Component
public class CallbackHandler implements ConfirmCallbackHandler, ReturnCallbackHandler {


    @Override
    public String getCallKey() {
        return CallbackHandler.class.getName();
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("in confirm");
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("in return");
    }
}
