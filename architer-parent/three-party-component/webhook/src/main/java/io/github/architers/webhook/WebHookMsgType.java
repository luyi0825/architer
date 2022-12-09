package io.github.architers.webhook;

import lombok.Getter;

@Getter
public enum WebHookMsgType {
    /**
     * 文本
     */
    TEXT("text", "文本类型"),

    /**
     * Link消息
     */
    LINK("link", "link"),

    MARKDOWN("markdown", "markdown"),

    ACTION_CARD("actionCard", "整体跳转actionCard"),

    FEED_CARD("feedCard", "feedCard");

    /**
     * 消息类型
     */
    private final String msgType;

    /**
     * 中文说明
     */
    private final String caption;

    WebHookMsgType(String msgType, String caption) {
        this.msgType = msgType;
        this.caption = caption;
    }
}
