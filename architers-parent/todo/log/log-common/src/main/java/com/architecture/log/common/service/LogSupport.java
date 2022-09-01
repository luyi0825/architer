package io.github.architers.log.common.service;


import io.github.architers.log.common.enums.LogType;
import io.github.architers.log.common.LogMeta;

/**
 * @author luyi
 */
public interface LogSupport {
    /**
     * @param logType
     * @return
     */
    boolean support(LogType logType);

    void valid(LogMeta logMeta);

}
