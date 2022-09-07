package io.github.architers.cloud.common;

/***
 * 负载均衡策略
 * @author luyi
 */
public enum LoadBalanceStrategy {
    /**
     * 权重
     */
    weight,
    /**
     * 随机
     */
    random,
    /**
     * 轮训
     */
    round
}
