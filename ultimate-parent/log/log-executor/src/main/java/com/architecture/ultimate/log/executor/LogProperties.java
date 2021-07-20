package com.architecture.ultimate.log.executor;


import com.architecture.ultimate.thread.properties.TaskExecutorProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author admin
 */
@ConfigurationProperties("customize.log")
public class LogProperties extends TaskExecutorProperties {
    /**
     * 异步执行，只有true,才会初始化LogExecutor
     *
     * @see LogExecutorConfig#logExecutor
     */
    private boolean async = true;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
