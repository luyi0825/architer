package io.github.architers.webhook.wechat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 企微text信息
 *
 * @author luyi
 */
@Data
public class WechatText {

    /**
     * 文本内容
     */
    private String content;

    /**
     * 用户ID
     */
    @JsonProperty("mentioned_list")
    private List<String> mentionedList;

    /**
     * 手机号列表
     */
    @JsonProperty("mentioned_mobile_list")
    private List<String> mentionedMobileList;

}
