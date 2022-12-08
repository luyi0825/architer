package io.github.architers.webhook.service.impl;


import io.github.architers.context.utils.JsonUtils;
import io.github.architers.webhook.feign.IDingDingRemoteService;
import io.github.architers.webhook.model.WebHookMessage;
import io.github.architers.webhook.service.IWebHookService;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 企业微信webHook对应的service实现类
 *
 * @author luyi
 */
@Service
public class WebHookServiceImpl implements IWebHookService {


    @Resource
    private IDingDingRemoteService dingDingRemoteService;

    @SneakyThrows
    @Override
    public void sendMessage(String key, WebHookMessage webHookMessage) {


        String response = dingDingRemoteService.send(key,
                JsonUtils.toJsonString(webHookMessage));
        System.out.println(response);
    }
}
