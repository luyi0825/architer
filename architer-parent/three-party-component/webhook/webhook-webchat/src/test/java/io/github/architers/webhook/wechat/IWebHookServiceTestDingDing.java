//package io.github.architers.webhook.wechat;
//
//import io.github.architers.webhook.constants.WebHookMsgType;
//
//import io.github.architers.webhook.dingding.model.*;
//import io.github.architers.webhook.service.IWebHookService;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//
//import java.util.Arrays;
//import java.util.Collections;
//
//
//@SpringBootTest
//class IWebHookServiceTestDingDing {
//
//    @Resource
//    private IWebHookService webHookService;
//
//    /**
//     * 发送text消息
//     */
//    @Test
//    void sendTextMessage() {
//        DingDingTextMessage textMessage = new DingDingTextMessage();
//        DingDingAt dingDingAt = new DingDingAt();
//        dingDingAt.setAtMobiles(Collections.singleton("15826509223"));
//        textMessage.setMsgtype(WebHookMsgType.TEXT.getMsgType());
//        textMessage.setAt(dingDingAt);
//        DingDingText dingDingText = new DingDingText();
//        dingDingText.setContent("我弟弟来了，你多照顾照顾他哟！");
//        textMessage.setText(dingDingText);
//        https:
////oapi.dingtalk.com/robot/send?access_token=d68e0ff73e72685f2ab62b5a8b167fe18a6365e7f4c7579854e52badac060549
//        webHookService.sendMessage("03ebbb352c3dd574dbdf0d7db34a21c32182f02aeb483a52cf0bdef315b25d20", textMessage);
//    }
//
//    /**
//     * 发送markdown消息
//     */
//    @Test
//    void sendMarkDownMessage() {
//        DingDingAt dingDingAt = new DingDingAt();
//        dingDingAt.setAtMobiles(Collections.singleton("15826509223"));
//        DingDingMarkDown dingDingMarkDown = new DingDingMarkDown();
//        dingDingMarkDown.setText("我弟弟来了，你多照顾照顾他哟！");
//        dingDingMarkDown.setTitle("彭大狗请注意");
//        DingDingMarkdownMessage markdownMessage = new DingDingMarkdownMessage(dingDingMarkDown, dingDingAt);
//        https:
////oapi.dingtalk.com/robot/send?access_token=d68e0ff73e72685f2ab62b5a8b167fe18a6365e7f4c7579854e52badac060549
//        webHookService.sendMessage("03ebbb352c3dd574dbdf0d7db34a21c32182f02aeb483a52cf0bdef315b25d20", markdownMessage);
//    }
//
//    @Test
//    void sendCardMessage() {
//        DingDingActionCard actionCard = new DingDingActionCard();
//        actionCard.setTitle("彭大狗请注意");
//        actionCard.setSingleTitle("明天五狗也会加入我们这个群体");
//        actionCard.setText("![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)### 乔布斯 20 年前想打造的苹果咖啡厅 Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划");
//        actionCard.setSingleURL("https://www.dingtalk.com/");
//        DingDingActionCardMessage dingDingActionCardMessage = new DingDingActionCardMessage(actionCard);
//        webHookService.sendMessage("03ebbb352c3dd574dbdf0d7db34a21c32182f02aeb483a52cf0bdef315b25d20", dingDingActionCardMessage);
//    }
//
//    @Test
//    void sendLinkMessage() {
//        DingDingLink link = new DingDingLink();
//        link.setTitle("测试");
//        link.setPicUrl("https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png");
//        link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
//        link.setMessageUrl("https://www.dingtalk.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
//        DingDingLinkMessage linkMessage = new DingDingLinkMessage(link);
//        webHookService.sendMessage("03ebbb352c3dd574dbdf0d7db34a21c32182f02aeb483a52cf0bdef315b25d20", linkMessage);
//    }
//
//    @Test
//    public void sendFeedCardMessage() {
//        DingDingFeedCard.Link link = new DingDingFeedCard.Link();
//        link.setText("text");
//        link.setTitle("title");
//        link.setPicUrl("https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png");
//
//        DingDingFeedCard feedCard = new DingDingFeedCard( Arrays.asList(link,link));
//        DingDingFeedCardMessage dingDingActionCardMessage  =
//                new DingDingFeedCardMessage(feedCard);
//        webHookService.sendMessage("03ebbb352c3dd574dbdf0d7db34a21c32182f02aeb483a52cf0bdef315b25d20", dingDingActionCardMessage);
//    }
//
//
//}