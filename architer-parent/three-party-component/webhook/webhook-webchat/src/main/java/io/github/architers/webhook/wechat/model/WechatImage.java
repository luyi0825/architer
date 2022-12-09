package io.github.architers.webhook.wechat.model;

import lombok.Data;

/**
 * 图片消息
 * @author luyi
 */
@Data
public class WechatImage {
    /**
     * 图片内容的base64编码
     */
    private String base64;

    /**
     * 图片内容（base64编码前）的md5值
     */
    private String md5;
}
