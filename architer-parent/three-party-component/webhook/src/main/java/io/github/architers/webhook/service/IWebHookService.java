package io.github.architers.webhook.service;


import io.github.architers.webhook.model.WebHookMessage;

import java.security.InvalidKeyException;

/**
 * 企业微信webHook对应的service
 * @author luyi
 */
public interface IWebHookService {

    /**
     * 发送消息
     */
     void sendMessage(String key, WebHookMessage webHookMessage);

}
