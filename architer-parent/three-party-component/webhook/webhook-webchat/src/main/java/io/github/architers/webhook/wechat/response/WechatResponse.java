package io.github.architers.webhook.wechat.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信响应结果
 *
 * @author luyi
 */
@Data
public class WechatResponse implements Serializable {
    /**
     * 错误的编码
     */
    private int errcode;
    /**
     * 错误消息
     */
    private String errmsg;
}
