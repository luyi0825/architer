package com.architecture.ultimate.mq.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

public class CallBackMessage extends Message {

    /**
     * 消息没有到达队列的标识key
     */
    private String returnKey;

    public CallBackMessage(byte[] body, MessageProperties messageProperties) {
        super(body, messageProperties);
    }

    public String getReturnKey() {
        return returnKey;
    }

    public CallBackMessage setReturnKey(String returnKey) {
        this.returnKey = returnKey;
        return this;
    }
}
