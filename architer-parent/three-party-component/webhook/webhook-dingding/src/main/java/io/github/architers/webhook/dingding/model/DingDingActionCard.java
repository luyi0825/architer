package io.github.architers.webhook.dingding.model;

import lombok.Data;

/**
 * @author luyi
 */
@Data
public class DingDingActionCard {

    /**
     * 首屏会话透出的展示内容
     */
    private String title;

    /**
     * 格式的消息
     */

    private String text;


    /**
     * 单个按钮的标题
     */
    private String singleTitle;
    /**
     * 点击消息跳转的UR
     */

    private String singleURL;

    /**
     * 0：按钮竖直排列|1：按钮横向排列
     */
    private String btnOrientation;


}
