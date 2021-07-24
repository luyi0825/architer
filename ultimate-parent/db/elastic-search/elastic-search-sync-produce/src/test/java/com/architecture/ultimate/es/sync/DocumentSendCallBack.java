package com.architecture.ultimate.es.sync;

import com.architecture.ultimate.mq.rabbit.callback.ConfirmCallbackHandler;
import com.architecture.ultimate.mq.rabbit.callback.ReturnCallbackHandler;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Component;

@Component
public class DocumentSendCallBack implements ConfirmCallbackHandler, ReturnCallbackHandler {
    @Override
    public String getCallBackId() {
        return DocumentSendCallBack.class.getName();
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            System.out.println("false" + correlationData.getId());
        }
    }


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("returnedMessage:" + replyCode);
    }
}
