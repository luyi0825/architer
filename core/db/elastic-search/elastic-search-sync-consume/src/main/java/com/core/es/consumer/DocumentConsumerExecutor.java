package com.core.es.consumer;

import com.core.thread.CommonTaskExecutor;
import com.core.thread.properties.ThreadPoolConfig;

public class DocumentConsumerExecutor extends CommonTaskExecutor {

    public DocumentConsumerExecutor(ThreadPoolConfig threadPoolConfig){
        super(threadPoolConfig);
    }

}
