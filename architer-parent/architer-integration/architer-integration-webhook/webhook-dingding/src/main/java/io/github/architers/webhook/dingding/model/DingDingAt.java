package io.github.architers.webhook.dingding.model;

import lombok.Data;

import java.util.Set;

@Data
public class DingDingAt {
    /**
     * 被@人的手机号
     */
    private Set<String> atMobiles;

    /**
     * 被@人的用户userid
     */
    private Set<String> atUserIds;

    /**
     * 是否@所有人
     */
    private Boolean isAtAll;
}
