package com.architecture.ultimate.log.common.service;


import com.architecture.ultimate.log.common.enums.LogType;
import com.architecture.ultimate.log.common.LogMeta;

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
