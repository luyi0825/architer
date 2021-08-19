package com.architecture.mq.rabbit.callback;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author luyi
 */
public class CallbackCorrelationData extends CorrelationData {
    /**
     * 消息回调标识
     */
    private String callBackId;

    public CallbackCorrelationData() {
    }

    public CallbackCorrelationData(String id) {
        super(id);
    }

    public String getCallBackId() {
        return callBackId;
    }

    public CallbackCorrelationData setCallBackId(String callBackId) {
        this.callBackId = callBackId;
        return this;
    }
}
