package com.architecture.ultimate.mq.rabbit;

/**
 * 重试操作
 *
 * @author luyi
 */
public enum RetryType {
    /**
     * 重回对列自己消费
     */
    REQUEUE_SELF(),
    /**
     * 重回对列,可以供自己处理
     */
    REQUEUE()

}
