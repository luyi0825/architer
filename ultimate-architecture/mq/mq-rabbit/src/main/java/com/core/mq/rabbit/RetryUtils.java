package com.core.mq.rabbit;

import com.core.cache.redis.StringRedisService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 * 重试工具类
 */
@Component
public class RetryUtils {
    private StringRedisService redisService;
    private final Logger logger = LoggerFactory.getLogger(RetryUtils.class);

    /**
     * 异常重试，我们可以选择重回对列
     * 当达到最大的重试次数，配置了死信对列，则进入死信对列，否则消息丢失
     *
     * @param message   消息
     * @param channel   消息管道
     * @param retryType 重试类型
     * @see RetryType#REQUEUE_SELF 重回对列让自己消费
     * @see RetryType#REQUEUE 重回对列都可以消费
     */
    public void exceptionRetry(Message message, Channel channel, RetryType retryType) throws IOException {
        try {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            Integer maxTryCount = (Integer) headers.get(RabbitmqConstants.MAX_RETRY_COUNT);
            if (maxTryCount != null && maxTryCount > 0) {
                String queue = message.getMessageProperties().getConsumerQueue();
                String messageId = message.getMessageProperties().getHeader(RabbitmqConstants.RETRY_KEY);
                String key = "mq_retry_" + queue + "::" + messageId;
                Integer tryCount = (Integer) redisService.get(key, 60 * 30L);
                if (tryCount == null) {
                    tryCount = 0;
                }
                if (tryCount >= maxTryCount) {
                    //达到最大的重试次数
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                    logger.info("basicNack消息：{},{}", queue, messageId);
                }
                //重试次数加1
                redisService.increment(key, 1);
                if (RetryType.REQUEUE_SELF.equals(retryType)) {
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    logger.info("重回队列：{},{}（重试{}次）", queue, messageId, tryCount);
                } else if (RetryType.REQUEUE.equals(retryType)) {
                    //重回对列,并让其他消费者消费
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                    logger.info("重回队列：{},{}（重试{}次）", queue, messageId, tryCount);
                }
            }
        } catch (Exception e) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

    }

    @Autowired(required = false)
    public void setRedisService(StringRedisService redisService) {
        this.redisService = redisService;
    }
}
