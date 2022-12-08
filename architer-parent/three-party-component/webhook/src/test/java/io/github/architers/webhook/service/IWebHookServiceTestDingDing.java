package io.github.architers.webhook.service;

import io.github.architers.webhook.WebHookMsgType;
import io.github.architers.webhook.model.dingding.DingDingText;
import io.github.architers.webhook.model.dingding.DingDingTextMessage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


import java.util.HashSet;
import java.util.List;


@SpringBootTest
class IWebHookServiceTestDingDing {

    @Resource
    private IWebHookService webHookService;

    /**
     * 发送text消息
     */
    @Test
    void sendTextMessage() {
        DingDingTextMessage textMessage = new DingDingTextMessage();
        textMessage.setMsgtype(WebHookMsgType.TEXT.getMsgType());
        textMessage.setAtMobiles(new HashSet<>(List.of("15826503223")));
        DingDingText dingDingText = new DingDingText();
        dingDingText.setContent("彭大狗，我是二狗呀！");
        textMessage.setText(dingDingText);
        webHookService.sendMessage("03ebbb352c3dd574dbdf0d7db34a21c32182f02aeb483a52cf0bdef315b25d20", textMessage);
    }
}