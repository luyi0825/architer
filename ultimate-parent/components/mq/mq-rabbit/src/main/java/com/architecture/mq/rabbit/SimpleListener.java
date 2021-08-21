package com.architecture.mq.rabbit;


import com.architecture.context.cache.CacheService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author admin
 */
public class SimpleListener {


    private final String queue;
    /**
     * 最大重试次数
     */
    private final int maxRetryCount;

    public SimpleListener(String queue, int maxRetryCount) {
        this.queue = queue;
        this.maxRetryCount = maxRetryCount;
    }

    @Autowired
    private CacheService cacheService;

    protected void process(Message message, Channel channel) {
        if (canProcess(message.getMessageProperties().getMessageId())) {
        }
    }


    /**
     * 是否能够处理
     *
     * @author luyi
     * @date 2021/4/22
     */
    public boolean canProcess(String messageId) {
        String consumeCacheKey = queue + "::" + messageId;
        //判断是否已经消费
        if (cacheService.get(consumeCacheKey) != null) {
            return false;
        }
        return true;
    }


}
