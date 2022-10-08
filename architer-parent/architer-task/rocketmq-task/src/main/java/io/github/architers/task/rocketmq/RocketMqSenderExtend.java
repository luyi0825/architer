package io.github.architers.task.rocketmq;

import io.github.architers.context.task.send.SenderExtend;

/**
 * @author luyi
 */
public interface RocketMqSenderExtend extends SenderExtend {

    /**
     * 是否异步
     *
     * @return true标识异步发送
     */
    boolean async();

    /**
     * 是否有序
     * @return true:就会发送有序
     */
    boolean orderly();
}
