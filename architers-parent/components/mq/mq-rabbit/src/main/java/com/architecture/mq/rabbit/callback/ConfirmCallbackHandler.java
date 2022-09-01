package io.github.architers.mq.rabbit.callback;


import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * rabbitMq消息确认回调处理
 *
 * @author luyi
 */
public interface ConfirmCallbackHandler extends RabbitTemplate.ConfirmCallback, SendCallBack {

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
