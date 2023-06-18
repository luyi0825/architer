package io.github.architers.webhook.dingding.model;


import lombok.Data;

import java.util.List;

/**
 * @author luyi
 */
@Data
public class DingDingFeedCard {

    private List<Link> links;

    public DingDingFeedCard(List<Link> links) {
        this.links = links;
    }

    public DingDingFeedCard() {

    }

    @Data
    public static class Link {
        /**
         * 消息标题
         */
        private String title;
        /**
         * 消息内容。
         */
        private String text;
        /**
         * 图片url
         */
        private String picUrl;
    }
}
