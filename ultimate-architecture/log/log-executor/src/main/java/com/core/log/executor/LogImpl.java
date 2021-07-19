package com.core.log.executor;

import com.core.log.common.LogMeta;
import com.core.log.common.service.Log;
import com.core.log.common.service.LogAbstract;
import com.core.log.common.service.LogProcessor;
import com.core.log.common.service.LogSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * @author 日志执行器
 */
@Component
public class LogImpl extends LogAbstract {

    private final List<LogProcessor> logProcessors;

    @Autowired
    public LogImpl(List<LogProcessor> logProcessors) {
        this.logProcessors = logProcessors;
    }


    @Override
    protected void doLog(LogMeta logMeta) {
        logProcessors.forEach(logProcessor -> {
            if (logProcessor.support(logMeta.getLogType())) {
                //校验数据
                logProcessor.valid(logMeta);
                //执行记录
                if (logMeta.isAsync()) {
                    logMeta.getExecutor().submit(() -> {
                        logProcessor.log(logMeta);
                    });
                } else {
                    logProcessor.log(logMeta);
                }
            }
        });
    }


}
