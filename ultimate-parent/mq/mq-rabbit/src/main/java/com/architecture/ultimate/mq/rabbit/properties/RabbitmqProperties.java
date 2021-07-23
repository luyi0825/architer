package com.architecture.ultimate.mq.rabbit.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luyi
 * rabbitMq的自定义配置属性
 */
@ConfigurationProperties(prefix = "customize.rabbitmq")
public class RabbitmqProperties {
    /**
     * 是否重试
     */
    private boolean retry = true;

    private boolean callBack = true;


    public boolean isCallBack() {
        return callBack;
    }

    public void setCallBack(boolean callBack) {
        this.callBack = callBack;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }
}
