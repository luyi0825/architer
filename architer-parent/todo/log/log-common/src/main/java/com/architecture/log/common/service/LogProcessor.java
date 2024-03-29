package io.github.architers.log.common.service;


import io.github.architers.log.common.LogMeta;

/**
 * @author luyi
 */
public abstract class LogProcessor implements LogSupport {
    public abstract void log(LogMeta logMeta);

    @Override
    public void valid(LogMeta logMeta) {
        if (logMeta.isAsync() && logMeta.getExecutor() == null) {
            throw new IllegalArgumentException("log executor is null");
        }
    }
}
