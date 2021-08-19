package com.architecture.log.common.service;


import com.architecture.log.common.enums.LogType;
import com.architecture.log.common.LogMeta;

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
