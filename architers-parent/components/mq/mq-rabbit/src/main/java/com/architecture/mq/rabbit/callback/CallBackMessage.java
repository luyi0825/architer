package io.github.architers.mq.rabbit.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

public class CallBackMessage extends Message {

    /**
     * 消息回调标识
     */
    private String callBackId;

    public CallBackMessage(byte[] body, MessageProperties messageProperties) {
        super(body, messageProperties);
    }

    public CallBackMessage(String callBackId, byte[] body, MessageProperties messageProperties) {
        super(body, messageProperties);
        this.callBackId = callBackId;
    }

    public String getCallBackId() {
        return callBackId;
    }

    public CallBackMessage setCallBackId(String callBackId) {
        this.callBackId = callBackId;
        return this;
    }
}
