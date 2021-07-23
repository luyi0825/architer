package com.architecture.ultimate.mq.rabbit.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

public class CallBackMessage extends Message {

    /**
     * 消息回调标识
     */
    private String callBackKey;

    public CallBackMessage(byte[] body, MessageProperties messageProperties) {
        super(body, messageProperties);
    }

    public CallBackMessage(String callBackKey, byte[] body, MessageProperties messageProperties) {
        super(body, messageProperties);
        this.callBackKey = callBackKey;
    }

    public String getCallBackKey() {
        return callBackKey;
    }

    public CallBackMessage setCallBackKey(String callBackKey) {
        this.callBackKey = callBackKey;
        return this;
    }
}
