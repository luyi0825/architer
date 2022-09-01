package io.github.architers.mq.rabbit.send;

import io.github.architers.mq.rabbit.callback.CallBackMessage;
import io.github.architers.mq.rabbit.callback.CallbackCorrelationData;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * rabbitMq数据发送器
 * 对数据发送进行了统一的封装，发送前进行了各种参数数据校验
 *
 * @author luyi
 */
@Component
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送需要发布确认的消息
     */
    public void sendForPublishConfirm(String exchange, String routingKey, CallBackMessage message, CallbackCorrelationData correlationData) {
        Assert.hasText(message.getCallBackId(), "message callBackId is null");
        Assert.hasText(correlationData.getCallBackId(), "correlationData callBackId is null");
        rabbitTemplate.send(exchange, routingKey, message, correlationData);
    }

    public void send(String routingKey, Message message) {
        rabbitTemplate.send(routingKey, message);
    }


    public void send(String exchange, String routingKey, Message message) {
        rabbitTemplate.send(exchange, routingKey, message);
    }

    public void send(String exchange, String routingKey, Message message, CorrelationData correlationData) {
        rabbitTemplate.send(exchange, routingKey, message, correlationData);
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
