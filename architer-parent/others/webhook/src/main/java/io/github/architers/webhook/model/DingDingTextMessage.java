package io.github.architers.webhook.model;

import lombok.Data;

import java.util.Set;

/**
 * 企微WebHook消息
 *
 * @author luyi
 */
@Data
public class DingDingTextMessage implements WebHookMessage {

    /**
     * 消息类型
     */
    private String msgtype;
    private DingDingText text;

    /**
     * 被@人的手机号
     */
    private Set<String> atMobiles;

    /**
     * 被@人的用户userid
     */
    private Set<String> atUserIds;

    /**
     * 是否@所有人
     * @param msgtype
     * @param text
     */
    private Boolean isAtAll;


    public DingDingTextMessage() {
    }



    public DingDingTextMessage(String msgtype, DingDingText text) {
        this.msgtype = msgtype;
        this.text = text;
    }
}
