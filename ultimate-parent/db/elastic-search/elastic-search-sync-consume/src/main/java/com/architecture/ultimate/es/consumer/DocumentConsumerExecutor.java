package com.architecture.ultimate.es.consumer;


import com.architecture.ultimate.thread.BaseTaskExecutor;
import com.architecture.ultimate.thread.properties.ThreadPoolConfig;

/**
 * @author luyi
 * es消费的线程池
 */
public class DocumentConsumerExecutor extends BaseTaskExecutor {

    public DocumentConsumerExecutor(ThreadPoolConfig threadPoolConfig) {
        super(threadPoolConfig);
    }

}
