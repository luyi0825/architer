//package io.github.architers.log.executor;
//
//
//import io.github.architers.log.common.LogMeta;
//import io.github.architers.log.common.service.LogProcessor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
//
///**
// * @author 日志执行器
// */
//@Component
//public class LogImpl extends io.github.architers.lol.common.service.LogAbstract {
//
//    private final List<LogProcessor> logProcessors;
//
//    @Autowired
//    public LogImpl(List<LogProcessor> logProcessors) {
//        this.logProcessors = logProcessors;
//    }
//
//
//    @Override
//    protected void doLog(LogMeta logMeta) {
//        logProcessors.forEach(logProcessor -> {
//            if (logProcessor.support(logMeta.getLogType())) {
//                //校验数据
//                logProcessor.valid(logMeta);
//                //执行记录
//                if (logMeta.isAsync()) {
//                    logMeta.getExecutor().submit(() -> {
//                        logProcessor.log(logMeta);
//                    });
//                } else {
//                    logProcessor.log(logMeta);
//                }
//            }
//        });
//    }
//
//
//}
