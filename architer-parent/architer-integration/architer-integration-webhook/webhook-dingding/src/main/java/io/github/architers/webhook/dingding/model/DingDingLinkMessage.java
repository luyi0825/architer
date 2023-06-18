package io.github.architers.webhook.dingding.model;

import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.dingding.DingDingMsgType;
import lombok.Data;

/**
 * @author luyi
 */
@Data
public class DingDingLinkMessage implements WebHookMessage {
    private String msgtype = DingDingMsgType.LINK.getMsgType();

    private DingDingLink link;

    public DingDingLinkMessage(DingDingLink link) {
        this.link = link;
    }

    public DingDingLinkMessage() {

    }
}