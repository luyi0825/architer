package com.core.log.executor;

import com.core.thread.CommonTaskExecutor;
import com.core.thread.properties.ThreadPoolConfig;

/**
 * @author luyi
 */
public class LogExecutor extends CommonTaskExecutor {
    public LogExecutor(ThreadPoolConfig threadPoolConfig) {
        super(threadPoolConfig);
    }
//    public LogExecutor(LogProperties taskExecutorProperties) {
//        super(taskExecutorProperties);
//    }
}
