package io.github.architers.webhook.model.dingding;

import io.github.architers.webhook.WebHookMsgType;
import io.github.architers.webhook.model.WebHookMessage;
import lombok.Data;

@Data
public class DingDingFeedCardMessage implements WebHookMessage {
    private String msgtype = WebHookMsgType.FEED_CARD.getMsgType();

    private DingDingFeedCard feedCard;

    public DingDingFeedCardMessage(DingDingFeedCard feedCard) {
        this.feedCard = feedCard;
    }

    public DingDingFeedCardMessage() {

    }
}
