package io.github.architers.mq.rabbit.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author luyi
 * 消息没有到队列的处理器
 */
public interface ReturnCallbackHandler extends RabbitTemplate.ReturnsCallback, SendCallBack {

}
