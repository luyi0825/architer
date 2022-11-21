package io.github.architers.server.file.service.impl;

import io.github.architers.server.file.dao.FileTaskRecordDao;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.domain.entity.TaskRecord;
import io.github.architers.server.file.service.IFileTaskService;
import io.github.architers.server.file.service.ITaskRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
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
}
