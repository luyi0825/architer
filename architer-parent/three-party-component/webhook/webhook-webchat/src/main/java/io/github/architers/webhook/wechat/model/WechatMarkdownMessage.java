package io.github.architers.webhook.wechat.model;

import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.wechat.WechatMsgType;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class WechatMarkdownMessage implements WebHookMessage {

    /**
     * 消息类型
     */
    private String msgtype = WechatMsgType.MARKDOWN.getMsgType();

    /**
     * markdown
     */
    private WechatMarkdown markdown;

    public WechatMarkdownMessage(WechatMarkdown markdown) {
        this.markdown = markdown;
    }

    public WechatMarkdownMessage() {

    }
}
