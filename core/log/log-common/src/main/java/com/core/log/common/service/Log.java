package com.core.log.common.service;

import com.core.log.common.LogMeta;
import com.core.log.common.enums.LogType;

/**
 * @author admin
 */
public interface Log {

    void execute(LogFunction logFunction);
}
