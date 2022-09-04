package io.github.architers.context.loadbalance;

import java.util.List;

/**
 * @author luyi
 * 负载均衡选择器
 */
public interface LoadBalanceSelector {


    /**
     * 选择一条数据
     *
     * @return 通过负载均衡选择的数据
     */
    Object selectOne(String selectKey, final List<?> data);

}
