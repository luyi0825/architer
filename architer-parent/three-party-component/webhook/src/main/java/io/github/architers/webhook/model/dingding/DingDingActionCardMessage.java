package io.github.architers.webhook.model.dingding;

import io.github.architers.webhook.WebHookMsgType;
import io.github.architers.webhook.model.WebHookMessage;
import lombok.Data;

@Data
public class DingDingActionCardMessage implements WebHookMessage {
    private String msgtype = WebHookMsgType.ACTION_CARD.getMsgType();

    private DingDingActionCard actionCard;

    public DingDingActionCardMessage(DingDingActionCard actionCard) {
        this.actionCard = actionCard;
    }

    public DingDingActionCardMessage() {
    }

}
