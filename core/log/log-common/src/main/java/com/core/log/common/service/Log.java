package com.core.log.common.service;

import com.core.log.common.enums.DBType;

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
    boolean log(DBType dBType, String schema, Serializable logInfo);
}
