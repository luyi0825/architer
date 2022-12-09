package io.github.architers.webhook.model.dingding;

import io.github.architers.webhook.WebHookMsgType;
import io.github.architers.webhook.model.WebHookMessage;
import lombok.Data;

/**
 * @author luyi
 */
@Data
public class DingDingLinkMessage implements WebHookMessage {
    private String msgtype = WebHookMsgType.LINK.getMsgType();

    private DingDingLink link;

    public DingDingLinkMessage(DingDingLink link) {
        this.link = link;
    }

    public DingDingLinkMessage() {

    }
}
