package io.github.architers.webhook.model.dingding;

import lombok.Data;

/**
 * @author luyi
 */
@Data
public class DingDingMarkDown {

    /**
     * 首屏会话透出的展示内容
     */
    private String title;
    /**
     * markdown格式的消息
     */
    private String text;

    public DingDingMarkDown() {

    }

    public DingDingMarkDown(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
