package com.core.log.common.service;

import com.core.log.common.enums.LogType;

import java.io.Serializable;

/**
 * @author admin
 */
public interface Log {

    /**
     * @param dBType
     * @param schema
     * @param logInfo
     * @return
     */
    boolean log(LogType dBType, String schema, Serializable logInfo);
}
