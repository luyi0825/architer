package com.architecture.log.common;

import com.architecture.log.common.enums.LogType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author luyi
 * 日志信息
 */
public class LogMeta {
    private LogType logType;
    private String schema;
    private boolean async = true;
    private ThreadPoolTaskExecutor executor;


    public ThreadPoolTaskExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }




}
