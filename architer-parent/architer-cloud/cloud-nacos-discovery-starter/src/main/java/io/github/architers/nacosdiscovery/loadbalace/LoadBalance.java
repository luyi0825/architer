package io.github.architers.nacosdiscovery.loadbalace;

/***
 * 负载均衡方式
 * @author luyi
 */
public enum LoadBalance {
    /**
     * 权重
     */
    weight,
    /**
     * 集群权重
     */
    cluster_weight,
    /**
     * 随机
     */
    random,
    /**
     * 轮训
     */
    round_robin
}
