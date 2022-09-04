package io.github.architers.context.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
            boolean reset = selectKeyIndex.compareAndSet(index + 1, 1);
            if (reset) {
                return data.get(0);
            }
            //自旋
            return selectOne(selectKey, data);
        }
        return data.get(index);
    }

    private AtomicInteger initSelectKeyIndex(String selectKey) {
        //以多个key为锁，防止最开始锁住其他的数据初始化
        synchronized (selectKey.intern()) {
            AtomicInteger selectKeyIndex = roundIndex.get(selectKey);
            if (selectKeyIndex == null) {
                selectKeyIndex = new AtomicInteger(0);
                roundIndex.put(selectKey, selectKeyIndex);
            }
            return selectKeyIndex;
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        LoadBalanceSelector loadBalanceSelector = new RoundLoadBalanceSelector();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.print(loadBalanceSelector.selectOne("test", list));
                }
            }).start();

        }

    }
}
