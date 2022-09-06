package io.github.architers.nacosdiscovery.loadbalace;

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
