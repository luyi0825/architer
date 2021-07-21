package com.architecture.ultimate.mq.rabbit.send;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author luyi
 * 消息没有到队列的处理器
 */
public interface ReturnCallbackHandler extends RabbitTemplate.ReturnCallback {
    /**
     * 消息没有到队列的处理器的key
     *
     * @return 处理器的标识
     */
    String getReturnCallbackKey();

}
