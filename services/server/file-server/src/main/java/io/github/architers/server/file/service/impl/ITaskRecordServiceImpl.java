package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.server.file.dao.FileTaskRecordDao;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.domain.entity.TaskRecord;
import io.github.architers.server.file.enums.TaskStatusEnum;
import io.github.architers.server.file.service.IFileTaskService;
import io.github.architers.server.file.service.ITaskRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class ITaskRecordServiceImpl implements ITaskRecordService {

    @Resource
    private FileTaskRecordDao fileTaskRecordDao;

    @Override
    public Long initTask(String taskCode) {
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setTaskCode(taskCode);
        taskRecord.setStatus(1);
        fileTaskRecordDao.insert(taskRecord);
        return taskRecord.getId();
    }

    @Override
    public void updateById(TaskRecord taskRecord) {
        fileTaskRecordDao.updateById(taskRecord);
    }

    @Override
    public void updateProcessingResultWithOptimisticLock(TaskRecord taskRecord) {
        Wrapper<TaskRecord> updateWrapper = Wrappers.lambdaUpdate(TaskRecord.class)
                .eq(TaskRecord::getId, taskRecord.getId())
                .eq(TaskRecord::getStatus, TaskStatusEnum.PROCESSING.getStatus());
        fileTaskRecordDao.update(taskRecord, updateWrapper);
    }
}
