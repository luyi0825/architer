package io.github.architers.server.file.service.impl;

import io.github.architers.context.exception.BusException;
import io.github.architers.server.file.domain.entity.FileTaskConfig;
import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.service.ITaskConfigService;
import io.github.architers.server.file.service.TaskService;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

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

    private ConcurrentMap<String, List<FileTaskConfig>> taskLimitMap = new ConcurrentHashMap<>();
    @Resource
    private StreamBridge streamBridge;


    @Override
    public boolean sendTask(ExecuteTaskParam executeTaskParam) {
        //判断是否能够执行
        List<FileTaskConfig> fileTaskConfig = taskConfigService.findByTaskCode(executeTaskParam.getTaskCode());
        for (FileTaskConfig config : fileTaskConfig) {
            for (ITaskLimit limit : taskLimits) {
                if (!limit.canExecute(executeTaskParam, config)) {
                    //拒绝处理
                    throw new BusException("任务拒绝");
                }
            }
        }

        try {
            for (ITaskLimit limit : taskLimits) {
                limit.startExecute(executeTaskParam);
            }
            streamBridge.send("test-stream", executeTaskParam);
            return true;
        } catch (Exception e) {
            for (ITaskLimit limit : taskLimits) {
                limit.endExecute(executeTaskParam);
            }
        }
        return true;
    }


}
