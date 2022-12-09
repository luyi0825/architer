package io.github.architers.webhook.wechat.model;

import lombok.Data;

/**
 * 企微markdown
 *
 * @author luyi
 */
@Data
public class WechatMarkdown {

    /**
     * markdown的内容
     */
    private String content;

    public WechatMarkdown(String content) {
        this.content = content;
    }

    public WechatMarkdown() {

    }
}
