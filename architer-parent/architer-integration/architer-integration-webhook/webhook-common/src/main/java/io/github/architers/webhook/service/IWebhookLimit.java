package io.github.architers.webhook.service;

/**
 * @author Administrator
 */
public interface IWebhookLimit {

    /**
     * 准备发送
     *
     * @param robotKey 机器人标识key
     * @return 是否限流
     */
    boolean prepareSend(String robotKey);

    /**
     * 发送失败，还原发送次数
     *
     * @param robotKey 机器人标识key
     */
    void failedAndRestoreSendCount(String robotKey);
}
