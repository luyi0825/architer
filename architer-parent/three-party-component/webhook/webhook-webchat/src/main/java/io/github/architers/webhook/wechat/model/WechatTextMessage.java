package io.github.architers.webhook.wechat.model;

import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.wechat.WechatMsgType;
import lombok.Data;

/**
 * 企微text类型消息
 *
 * @author luyi
 */
@Data
public class WechatTextMessage implements WebHookMessage {

    /**
     * 消息类型
     */
    private String msgtype = WechatMsgType.TEXT.getMsgType();

    /**
     * text信息
     */
    private WechatText text;

    public WechatTextMessage(WechatText text) {
        this.text = text;
    }

    public WechatTextMessage() {

    }


}
