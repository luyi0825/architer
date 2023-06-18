package io.github.architers.webhook.service;


import io.github.architers.context.web.ResponseResult;
import io.github.architers.webhook.WebHookMessage;

/**
 * 企业微信webHook对应的service
 *
 * @author luyi
 */
public interface IWebHookService {

    /**
     * 发送消息
     *
     * @param rocketKey      机器人的key
     * @param webHookMessage 发送的消息
     * @return 发送返回的结果
     */
    ResponseResult<?> sendMessage(String rocketKey, WebHookMessage webHookMessage);


}
