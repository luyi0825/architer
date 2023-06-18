package io.github.architers.webhook.service.impl;

import io.github.architers.context.exception.BusErrorException;
import io.github.architers.webhook.WebhookProperties;
import io.github.architers.webhook.service.IWebhookLimit;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;

/**
 * redisson限流
 *
 * @author luyi
 */
public class RedissonWebhookLimit implements IWebhookLimit {

    @Resource
    private RedissonClient redissonClient;

    private final WebhookProperties webhookProperties;

    public RedissonWebhookLimit(WebhookProperties webhookProperties) {
        this.webhookProperties = webhookProperties;
    }


    @Override
    public boolean prepareSend(String robotKey) {
        WebhookProperties.RobotConfig robotConfig = webhookProperties.getRobotConfigs().get(robotKey);
        if (robotConfig == null) {
            throw new BusErrorException("没有配置机器人信息：" + robotKey);
        }
        WebhookProperties.Limit limit = robotConfig.getLimit();
        if (limit == null) {
            //如果没有配置限流，就取默认限流配置
            limit = webhookProperties.getDefaultLimit();
        }
        if (limit.getNum() <= 0) {
            //不限流
            return true;
        }
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(webhookProperties.getLimitKeyPrefix() + robotKey);

        rRateLimiter.trySetRate(RateType.OVERALL, limit.getNum(), limit.getTime(), RateIntervalUnit.SECONDS);
        return rRateLimiter.tryAcquire(1);
    }

    @Override
    public void failedAndRestoreSendCount(String robotKey) {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter(webhookProperties.getLimitKeyPrefix() + robotKey);
        rRateLimiter.acquire(-1);
    }
}
