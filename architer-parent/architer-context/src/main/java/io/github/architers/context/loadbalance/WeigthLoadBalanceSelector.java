package io.github.architers.context.loadbalance;

import java.util.List;

/**
 * 权重的负载均衡算法
 *
 * @author luyi
 */
public class WeigthLoadBalanceSelector implements LoadBalanceSelector {
    @Override
    public Object selectOne(String selectKey, List<?> data) {

        Math.random();

        // Arrays.binarySearch()

        return null;
    }
}
