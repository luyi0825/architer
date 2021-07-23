package com.architecture.ultimate.mq.rabbit.callback;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author luyi
 */
public class CallbackCorrelationData extends CorrelationData {
    /**
     * 消息回调标识
     */
    private String callBackKey;

    public CallbackCorrelationData() {
    }

    public CallbackCorrelationData(String id) {
        super(id);
    }

    public String getCallBackKey() {
        return callBackKey;
    }

    public void setCallBackKey(String callBackKey) {
        this.callBackKey = callBackKey;
    }
}
