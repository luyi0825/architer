package com.core.es.consumer;

import com.lz.thread.CommonTaskExecutor;
import com.lz.thread.properties.ThreadPoolConfig;

public class DocumentConsumerExecutor extends CommonTaskExecutor {

    public DocumentConsumerExecutor(ThreadPoolConfig threadPoolConfig){
        super(threadPoolConfig);
    }

}
