package io.github.architers.server.file.service.impl;

import io.github.architers.context.exception.BusException;
import io.github.architers.context.task.annotation.AsyncTask;
import io.github.architers.context.task.constants.SenderName;
import io.github.architers.context.web.ResponseResult;
import io.github.architers.server.file.domain.entity.TaskConfig;
import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.service.ITaskConfigService;
import io.github.architers.server.file.service.ITaskNodeService;
import io.github.architers.server.file.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private List<ITaskLimit> taskLimits;

    @Resource
    private ITaskConfigService taskConfigService;

    private ConcurrentMap<String, List<TaskConfig>> taskLimitMap = new ConcurrentHashMap<>();

    @Resource
    private TaskService taskService;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private ITaskNodeService taskNodeService;


    @Override
    public boolean sendTask(ExecuteTaskParam executeTaskParam) {
        //判断是否能够执行
        List<TaskConfig> taskConfig = taskConfigService.findByTaskCode(executeTaskParam.getTaskCode());
        for (TaskConfig config : taskConfig) {
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
            taskService.sendAsyncTask(executeTaskParam.getTaskCode(), executeTaskParam.getExecuteParam());
            return true;
        } catch (Exception e) {
            for (ITaskLimit limit : taskLimits) {
                limit.endExecute(executeTaskParam);
            }
        }
        return true;
    }


    @Override
    @AsyncTask(group = "selfTask", taskName = "task_store", sender = SenderName.ROCKET_MQ)
    public void sendAsyncTask(String taskCode, Map<String, Object> executeParam) {
        FileTask fileTask = taskNodeService.findByTaskCode(taskCode);
        ResponseResult<?> responseResult = restTemplate.postForEntity(fileTask.getTaskAddress(),
                executeParam, ResponseResult.class).getBody();
        assert responseResult != null;
        if (HttpStatus.OK.value() == responseResult.getCode()) {
            System.out.println("处理成功");
        }
    }

}
