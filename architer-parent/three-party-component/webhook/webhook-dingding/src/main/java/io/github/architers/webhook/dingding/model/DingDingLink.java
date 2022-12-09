package io.github.architers.webhook.dingding.model;

import lombok.Data;

@Data
public class DingDingLink {

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
    /**
     * 点击消息跳转的URL
     */
    private String messageUrl;
}
