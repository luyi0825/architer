package io.github.architers.contenxt.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 轮询的负载均衡选择器
 *
 * @author luyi
 */
public class RoundLoadBalanceSelector implements LoadBalanceSelector {

    Map<String, AtomicInteger> roundIndex = new ConcurrentHashMap<>();

    @Override
    public Object selectOne(String selectKey, List<?> data) {
        AtomicInteger selectKeyIndex = roundIndex.get(selectKey);
        if (selectKeyIndex == null) {
            selectKeyIndex = initSelectKeyIndex(selectKey);
        }
        int index = selectKeyIndex.getAndIncrement();
        if (index >= data.size()) {
            //说明已经到达了最大的长度,重置下表
            boolean reset = selectKeyIndex.compareAndSet(index + 1, 0);
            if (reset) {
                return data.get(0);
            }
            //自旋
            return selectOne(selectKey, data);
        }
        return data.get(index);
    }

    private AtomicInteger initSelectKeyIndex(String selectKey) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            AtomicInteger selectKeyIndex = roundIndex.get(selectKey);
            if (selectKeyIndex != null) {
                return selectKeyIndex;
            }
            return new AtomicInteger(0);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        LoadBalanceSelector loadBalanceSelector = new RoundLoadBalanceSelector();
        for (int i = 0; i < 100; i++) {
            System.out.println(loadBalanceSelector.selectOne("test", list));
        }

    }
}
