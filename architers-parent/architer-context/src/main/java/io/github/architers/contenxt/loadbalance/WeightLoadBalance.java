package io.github.architers.contenxt.loadbalance;

/**
 * @author luyi
 * 标明能够实现负载均衡
 */
public interface WeightLoadBalance {


    /**
     * 获取权重
     *
     * @return 权重数量
     */
    int getWeight();
}
