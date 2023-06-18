package io.github.architers.webhook.dingding.model;

import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.dingding.DingDingMsgType;
import lombok.Data;

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
    private String msgtype = DingDingMsgType.TEXT.getMsgType();
    private DingDingText text;

    /**
     * 需要@的人
     */
    private DingDingAt at;


    public DingDingTextMessage() {
    }


    public DingDingTextMessage(DingDingText text, DingDingAt dingDingAt) {
        this.text = text;
        this.at = dingDingAt;
    }
}
