package com.architecture.ultimate.log.executor;

import com.architecture.ultimate.thread.CommonTaskExecutor;
import com.architecture.ultimate.thread.properties.ThreadPoolConfig;

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
