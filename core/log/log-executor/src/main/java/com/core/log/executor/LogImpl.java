package com.core.log.executor;

import com.core.log.common.enums.LogType;
import com.core.log.common.service.Log;
import org.springframework.stereotype.Component;

import java.io.Serializable;


/**
 * @author 日志执行器
 */
@Component
public class LogImpl implements Log {


    @Override
    public boolean log(LogType dBType, String schema, Serializable logInfo) {
        return false;
    }
}
