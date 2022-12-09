package io.github.architers.webhook.model.dingding;

import io.github.architers.webhook.WebHookMsgType;
import io.github.architers.webhook.model.WebHookMessage;
import lombok.Data;

/**
 * 钉钉markDown消息
 *
 * @author luyi
 */
@Data
public class DingDingMarkdownMessage implements WebHookMessage {
    /**
     * 消息类型
     */
    private String msgtype = WebHookMsgType.MARKDOWN.getMsgType();

    /**
     * markdown信息
     */
    private DingDingMarkDown markdown;

    /**
     * 需要@at的人
     */
    private DingDingAt at;

    public DingDingMarkdownMessage(DingDingMarkDown markdown, DingDingAt at) {
        this.markdown = markdown;
        this.at = at;
    }

    public DingDingMarkdownMessage() {

    }
}
