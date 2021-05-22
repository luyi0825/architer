package com.lz.core.test.stream.comsumer.compont;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author luyi
 * 消息接收
 */
@Component
@EnableBinding(Sink.class)
public class MessageReceive {

    @Value("${server.port}")
    private String port;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        System.out.println("收到的消息：" + message.getPayload() + ":" + port);
    }
}
