package io.github.architers.webhook.wechat;

import lombok.Getter;

@Getter
public enum WechatMsgType {

    /**
     * 文本
     */
    TEXT("text", "文本类型"),

    /**
     * news
     */
    news("news", "news"),

    /**
     * Link消息
     */
    LINK("link","link"),

    MARKDOWN("markdown","markdown"),

    ACTION_CARD("actionCard","整体跳转actionCard"),

    FEED_CARD("feedCard","feedCard");

    /**
     * 消息类型
     */
    private final String msgType;

    /**
     * 中文说明
     */
    private final String caption;

    WechatMsgType(String msgType, String caption) {
        this.msgType = msgType;
        this.caption = caption;
    }
}
