package com.architecture.ultimate.es.consumer;


import com.architecture.ultimate.thread.CommonTaskExecutor;
import com.architecture.ultimate.thread.properties.ThreadPoolConfig;

/**
 * @author luyi
 * es消费的线程池
 */
public class DocumentConsumerExecutor extends CommonTaskExecutor {

    public DocumentConsumerExecutor(ThreadPoolConfig threadPoolConfig) {
        super(threadPoolConfig);
    }

}
