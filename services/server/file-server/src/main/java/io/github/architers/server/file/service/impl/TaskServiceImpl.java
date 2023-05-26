package io.github.architers.server.file.service.impl;

import io.github.architers.context.exception.BusLogException;
import io.github.architers.server.file.domain.entity.FileTaskLimitConfig;
import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.service.ITaskConfigService;
import io.github.architers.server.file.service.ITaskLimit;
import io.github.architers.server.file.service.TaskService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private List<ITaskLimit> taskLimits;
    @Resource
    private ITaskConfigService taskConfigService;

    @Resource
    private StreamBridge streamBridge;


    @Override
    public boolean sendTask(ExecuteTaskParam executeTaskParam) {
        //判断是否能够执行
        List<FileTaskLimitConfig> fileTaskLimitConfig = taskConfigService.findByTaskCode(executeTaskParam.getTaskCode());
        if (CollectionUtils.isEmpty(fileTaskLimitConfig)) {

        }
        for (FileTaskLimitConfig config : fileTaskLimitConfig) {
            for (ITaskLimit limit : taskLimits) {
                if (!limit.canExecute(executeTaskParam, config)) {
                    //拒绝处理
                    throw new BusLogException("任务拒绝");
                }
            }
        }

        try {
            for (ITaskLimit limit : taskLimits) {
                limit.startExecute(executeTaskParam);
            }
            return streamBridge.send("storeFileTask-out-0", executeTaskParam);
        } catch (Exception e) {
            for (ITaskLimit limit : taskLimits) {
                limit.endExecute(executeTaskParam);
            }
        }
        return true;
    }


}
