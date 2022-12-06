package io.github.architers.webhook.service.impl;


import feign.Response;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.webhook.feign.RemoteWechatService;
import io.github.architers.webhook.model.WebHookMessage;
import io.github.architers.webhook.service.IWebHookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 企业微信webHook对应的service实现类
 *
 * @author luyi
 */
@Service
public class WebHookServiceImpl implements IWebHookService {


    @Autowired(required = false)
    private RemoteWechatService remoteWechatService;

    @Override
    public void sendMessage(String key, WebHookMessage webHookMessage) {
        remoteWechatService.send(key, JsonUtils.toJsonString(webHookMessage));
    }
}
