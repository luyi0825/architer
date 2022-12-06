package io.github.architers.webhook.model;

import lombok.Data;

@Data
public class DingDingMarkdownMessage implements WebHookMessage {
    /**
     * 消息类型
     */
    private String msgtype;

    private DingDingText markdown;

    public DingDingMarkdownMessage(String msgtype, DingDingText markdown) {
        this.msgtype = msgtype;
        this.markdown = markdown;
    }

    public DingDingMarkdownMessage() {

    }
}
