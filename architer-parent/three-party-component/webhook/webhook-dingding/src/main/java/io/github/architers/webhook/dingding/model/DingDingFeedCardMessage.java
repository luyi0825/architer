package io.github.architers.webhook.dingding.model;


import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.dingding.DingDingMsgType;
import lombok.Data;

@Data
public class DingDingFeedCardMessage implements WebHookMessage {
    private String msgtype = DingDingMsgType.FEED_CARD.getMsgType();

    private DingDingFeedCard feedCard;

    public DingDingFeedCardMessage(DingDingFeedCard feedCard) {
        this.feedCard = feedCard;
    }

    public DingDingFeedCardMessage() {

    }
}
