package io.github.architers.context.loadbalance;

import java.util.List;

/**
 * 随机选择器
 *
 * @author luyi
 */
public class RandomLoadBalanceSelector implements LoadBalanceSelector {


    @Override
    public Object selectOne(String selectKey, List<?> data) {
        int randomIndex = (int) (Math.random() * data.size());
        return data.get(randomIndex);
    }
}
