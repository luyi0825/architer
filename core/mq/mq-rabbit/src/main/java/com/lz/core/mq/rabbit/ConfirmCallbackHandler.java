package com.lz.core.mq.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * rabbitMq消息确认回调处理
 * 用key，value的形式替换适配器模式，减少循环，提高效率
 *
 * @author luyi
 */
public interface ConfirmCallbackHandler extends RabbitTemplate.ConfirmCallback {

    /**
     * 得到处理的key,
     * 这个key不允许重复，一个实现类对应一个，标识区分
     *
     * @return 处理器标识key
     */
    String getConfirmCallbackKey();

    /**
     * 消息只要抵达了Broker,ack就会为true
     *
     * @param correlationData 当前消息的唯一关联数据
     * @param ack             消息是否能够接收
     * @param cause           失败的原因
     */
    @Override
    void confirm(CorrelationData correlationData, boolean ack, String cause);


}
