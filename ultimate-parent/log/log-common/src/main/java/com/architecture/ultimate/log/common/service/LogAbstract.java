package com.architecture.ultimate.lol.common.service;


import com.architecture.ultimate.log.common.LogMeta;
import com.architecture.ultimate.log.common.service.Log;
import com.architecture.ultimate.log.common.service.LogFunction;

public abstract class LogAbstract implements Log {

    @Override
    public void execute(LogFunction logFunction) {
        LogMeta logMeta = logFunction.getLogMeta();
        this.doLog(logMeta);
    }

    /**
     * 执行记录
     *
     * @param logMeta
     */
    protected abstract void doLog(LogMeta logMeta);
}
