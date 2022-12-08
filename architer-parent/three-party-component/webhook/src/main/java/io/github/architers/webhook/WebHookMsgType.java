package io.github.architers.webhook;

import lombok.Getter;

@Getter
public enum WebHookMsgType {
    /**
     * 文本
     */
    TEXT("text", "文本类型"),

    MARKDOWN("markdown", "markdown");

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
