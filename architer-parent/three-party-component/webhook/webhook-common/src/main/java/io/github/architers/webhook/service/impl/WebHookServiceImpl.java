package io.github.architers.webhook.service.impl;


import io.github.architers.context.exception.BusLogException;
import io.github.architers.common.json.JsonUtils;
import io.github.architers.context.webmvc.ResponseResult;
import io.github.architers.webhook.WebHookMessage;
import io.github.architers.webhook.WebhookProperties;
import io.github.architers.webhook.IMessageRemote;
import io.github.architers.webhook.exeception.WebHookLimitException;
import io.github.architers.webhook.service.IWebHookService;
import io.github.architers.webhook.service.IWebhookLimit;
import lombok.SneakyThrows;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业微信webHook对应的service实现类
 *
 * @author luyi
 */
public class WebHookServiceImpl implements IWebHookService {

    @Resource
    private List<IMessageRemote> messageRemotes;

    @Resource
    private WebhookProperties webhookProperties;

    @Resource
    private IWebhookLimit webhookLimit;


    @SneakyThrows
    @Override
    public ResponseResult<?> sendMessage(String robotKey, WebHookMessage webHookMessage) {
        WebhookProperties.RobotConfig robotConfig = webhookProperties.getRobotConfigs().get(robotKey);
        if (robotConfig == null) {
            throw new BusLogException("机器人配置为空");
        }
        if (!webhookLimit.prepareSend(robotKey)) {
            throw new WebHookLimitException();
        }
        try {
            for (IMessageRemote messageRemote : messageRemotes) {
                if (messageRemote.getRobotType().equals(robotConfig.getRobotType())) {
                      return messageRemote.sendMessage(robotKey,robotConfig.getKey(), JsonUtils.toJsonString(webHookMessage));
                }
            }
        } catch (Exception e) {
            webhookLimit.failedAndRestoreSendCount(robotKey);
            throw e;
        }

        return ResponseResult.fail("messageRemote不存在");
    }
}
