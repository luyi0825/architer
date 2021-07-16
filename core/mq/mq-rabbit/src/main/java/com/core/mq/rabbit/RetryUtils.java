package com.core.mq.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;
import java.util.Map;

/**
 * @author luyi
 * 重试工具类
 */
public final class RetryUtils {
    private static final String TRY_COUNT_KEY = "try_count";
    private static final String MAX_TRY_COUNT_KEY = "max_try_count";

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
    public static void exceptionRetry(Message message, Channel channel, RetryType retryType) throws IOException {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        Integer maxTryCount = (Integer) headers.get(MAX_TRY_COUNT_KEY);
        if (maxTryCount != null && maxTryCount > 0) {
            int tryCount = (int) headers.getOrDefault(TRY_COUNT_KEY, 0);
            if (tryCount >= maxTryCount) {
                //达到最大的重试次数
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
            headers.put(TRY_COUNT_KEY, tryCount);
            if (RetryType.REQUEUE_SELF.equals(retryType)) {
                channel.basicRecover(false);
            } else if (RetryType.REQUEUE.equals(retryType)) {
                //重回对列,并让其他消费者消费
                channel.basicRecover(true);
            }
        }
    }
}
