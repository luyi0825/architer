package com.lz.core.test.stream.provider.controller;

import com.lz.core.test.stream.provider.service.MessageProvider;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 * 发送消息的controller
 */
@RestController
public class SendMessageController {
    private MessageProvider messageProvider;

    @RequestMapping("/sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }


    @Autowired
    public SendMessageController setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
        return this;
    }
}
