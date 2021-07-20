package com.architecture.ultimate.mq.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * @author luyi
 */
public class CallbackCorrelationData extends CorrelationData {

    /**
     * 消息到达broker确认标识
     */
    private String confirmKey;


    public CallbackCorrelationData() {
    }

    public CallbackCorrelationData(String id) {
        super(id);
    }


    public String getConfirmKey() {
        return confirmKey;
    }

    public CallbackCorrelationData setConfirmKey(String confirmKey) {
        this.confirmKey = confirmKey;
        return this;
    }

}
