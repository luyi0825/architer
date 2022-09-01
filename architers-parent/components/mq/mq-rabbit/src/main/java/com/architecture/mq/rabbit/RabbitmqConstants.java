package io.github.architers.mq.rabbit;

public final class RabbitmqConstants {
    /**
     * 最大重试次数
     */
    public static final String MAX_RETRY_COUNT = "max_retry_count";
    /**
     * 重试的标识ID,当重试的时候，需要保证这个字段的值在当前队列唯一
     */
    public static final String RETRY_KEY = "RETRY_KEY";
}
