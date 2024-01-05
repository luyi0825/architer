package io.github.architers.webhook.wechat;

import io.github.architers.context.webmvc.ResponseResult;
import io.github.architers.webhook.service.IWebHookService;

import io.github.architers.webhook.wechat.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Slf4j
class WebHookServiceTestWeChat {

    @Resource
    private IWebHookService webHookService;

    /**
     * 发送text消息
     */
    @Test
    void sendTextMessage() {
        WechatTextMessage wechatTextMessage = new WechatTextMessage();
        WechatText wechatText = new WechatText();
        wechatText.setContent("测试消息");
        wechatText.setMentionedMobileList(List.of("17671125125"));
        wechatTextMessage.setText(wechatText);
        https:
//oapi.dingtalk.com/robot/send?access_token=d68e0ff73e72685f2ab62b5a8b167fe18a6365e7f4c7579854e52badac060549
        for (int i = 0; i < 21; i++) {
            ResponseResult<?> result = webHookService.sendMessage("test", wechatTextMessage);
            log.info("返回结果:{}",result);
        }

    }

    /**
     * 发送markdown消息
     */
    @Test
    void sendMarkDownMessage() {
        WechatMarkdown markdown = new WechatMarkdown();
        markdown.setContent("实时新增用户反馈<font color=\\\"warning\\\">132例</font>，请相关同事注意。\n" +
                "         >类型:<font color=\\\"comment\\\">用户反馈</font>\n" +
                "         >普通用户反馈:<font color=\\\"comment\\\">117例</font>\n" +
                "         >VIP用户反馈:<font color=\\\"comment\\\">15例</font>");
        WechatMarkdownMessage wechatMarkdownMessage = new WechatMarkdownMessage(markdown);
        ResponseResult<?> result = webHookService.sendMessage("test", wechatMarkdownMessage);
        System.out.println(result);
    }

    @Test
    void sendNewsMessage() {
        WechatNews.Article article = new WechatNews.Article();
        article.setTitle("中秋节礼品领取");
        article.setDescription("今年中秋节公司有豪礼相送");
        article.setUrl("www.qq.com");
        article.setPicurl("http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png");
        WechatNews wechatNews = new WechatNews(Arrays.asList(article, article, article));
        WechatNewsMessage newsMessage = new WechatNewsMessage(wechatNews);
        ResponseResult<?> result = webHookService.sendMessage("test", newsMessage);
        log.info("返回结果：{}", result);
    }


}