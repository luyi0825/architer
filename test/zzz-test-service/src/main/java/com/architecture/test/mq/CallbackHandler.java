package com.architecture.test.mq;


import com.architecture.mq.rabbit.callback.ConfirmCallbackHandler;
import com.architecture.mq.rabbit.callback.ReturnCallbackHandler;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @author luyi
 */
@Component
public class CallbackHandler implements ConfirmCallbackHandler, ReturnCallbackHandler {


    @Override
    public String getCallBackId() {
        return CallbackHandler.class.getName();
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("in confirm");
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {

    }
}
