package com.core.log.common.service;


import com.core.log.common.LogMeta;
import com.core.log.common.enums.LogType;

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
