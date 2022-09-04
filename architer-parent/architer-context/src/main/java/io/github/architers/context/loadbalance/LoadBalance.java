package io.github.architers.context.loadbalance;

/**
 * @author luyi
 * 标明能够实现负载均衡
 */
public interface LoadBalance {

    /**
     * 获取权重
     *
     * @return 权重数量
     */
    int getWeight();

}
