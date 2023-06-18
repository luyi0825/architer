package io.github.architers.context.cache.consistency;

import io.github.architers.context.cache.model.CacheChangeParam;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class LocalDelayDeleteTask implements Delayed {

    private long time;

    private CacheChangeParam changeParam;

    private LocalCacheOperate localCacheOperate;


    public LocalDelayDeleteTask(long time, CacheChangeParam changeParam, LocalCacheOperate localCacheOperate) {
        this.time = System.currentTimeMillis() + time;
        this.changeParam = changeParam;
        this.localCacheOperate = localCacheOperate;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        long d = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        // 根据剩余时间判断等于0 返回1 不等于0
        // 有可能大于0 有可能小于0  大于0返回1  小于返回-1
        return (d == 0) ? 0 : ((d > 0) ? 1 : -1);
    }
}
