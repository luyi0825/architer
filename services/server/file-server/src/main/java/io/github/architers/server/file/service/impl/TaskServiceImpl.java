package io.github.architers.server.file.service.impl;

import cn.hutool.core.lang.UUID;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.context.utils.UserUtils;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.domain.entity.FileTaskLimitConfig;
import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.eums.TransactionMessageResult;
import io.github.architers.server.file.mq.FileExportMessageSender;
import io.github.architers.server.file.service.ITaskConfigService;
import io.github.architers.server.file.service.ITaskLimit;
import io.github.architers.server.file.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Resource
    private List<ITaskLimit> taskLimits;
    @Resource
    private ITaskConfigService taskConfigService;

    @Resource
    private FileExportMessageSender fileExportMessageSender;


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
            //  return streamBridge.send("storeFileTask-out-0", executeTaskParam);
        } catch (Exception e) {
            for (ITaskLimit limit : taskLimits) {
                limit.endExecute(executeTaskParam);
            }
        }
        return true;
    }

    @Override
    public TransactionMessageResult executeExportTask(ExecuteTaskParam executeTaskParam) {
        FileTaskExportRecord fileTaskExportRecord = new FileTaskExportRecord();
        fileTaskExportRecord.setRequestId(UUID.fastUUID().toString(true));
        fileTaskExportRecord.setRequestBody(executeTaskParam.getRequestBody());
        fileTaskExportRecord.setStatus(TaskRecordStatusEnum.IN_LINE.getStatus());
        fileTaskExportRecord.setCreateBy(UserUtils.getUserId());
        fileTaskExportRecord.setUpdateBy(UserUtils.getUserId());
        fileTaskExportRecord.setTaskCode(executeTaskParam.getTaskCode());
        return fileExportMessageSender.sendExportTaskMessage(fileTaskExportRecord);
    }


}
