package io.github.architers.webhook.model.dingding;

import io.github.architers.webhook.WebHookMsgType;
import io.github.architers.webhook.model.WebHookMessage;
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
    private String msgtype = WebHookMsgType.TEXT.getMsgType();
    private DingDingText text;

    private At at;


    public DingDingTextMessage() {
    }


    public DingDingTextMessage(DingDingText text, At at) {
        this.text = text;
        this.at = at;
    }
}
