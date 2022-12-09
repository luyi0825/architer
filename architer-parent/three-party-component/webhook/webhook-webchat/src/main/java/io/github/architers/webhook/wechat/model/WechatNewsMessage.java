package io.github.architers.webhook.wechat.model;

import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.wechat.WechatMsgType;
import lombok.Data;

/**
 * 企微news消息
 *
 * @author luyi
 */
@Data
public class WechatNewsMessage implements WebHookMessage {

    /**
     * 消息类型
     */
    private String msgtype = WechatMsgType.news.getMsgType();

    /**
     * 文件信息
     */
    private WechatNews news;

    public WechatNewsMessage(WechatNews news) {
        this.news = news;
    }
}
