package io.github.architers.webhook.dingding.model;

import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.dingding.DingDingMsgType;
import lombok.Data;

/**
 * @author luyi
 */
@Data
public class DingDingActionCardMessage implements WebHookMessage {
    private String msgtype = DingDingMsgType.ACTION_CARD.getMsgType();

    private DingDingActionCard actionCard;

    public DingDingActionCardMessage(DingDingActionCard actionCard) {
        this.actionCard = actionCard;
    }

    public DingDingActionCardMessage() {
    }

}
