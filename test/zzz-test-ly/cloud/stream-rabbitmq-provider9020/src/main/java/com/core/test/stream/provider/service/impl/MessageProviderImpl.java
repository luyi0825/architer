package com.core.test.stream.provider.service.impl;

import com.core.test.stream.provider.service.MessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author luyi
 * 消息提供实现类
 */
@EnableBinding(Source.class)//定义消息的推送管道
public class MessageProviderImpl implements MessageProvider {
    /**
     * 消息发送管道
     */
    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String message = UUID.randomUUID().toString();
        boolean send = output.send(MessageBuilder.withPayload(message).build());
        System.out.println(send);
        System.out.println("UUID:" + message);
        return message;
    }
}
