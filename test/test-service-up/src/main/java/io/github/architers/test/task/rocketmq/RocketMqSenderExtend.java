package io.github.architers.test.task.rocketmq;

import io.github.architers.test.task.SenderExtend;

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
