package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.mapper.FileTaskRecordMapper;
import io.github.architers.server.file.domain.param.TaskRecordsQueryParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.enums.TaskStatusEnum;
import io.github.architers.server.file.service.ITaskRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class TaskRecordServiceImpl implements ITaskRecordService {

    @Resource
    private FileTaskRecordMapper fileTaskRecordMapper;

    @Override
    public Long initTask(String taskCode) {
        FileTaskExportRecord fileTaskExportRecord = new FileTaskExportRecord();
        fileTaskExportRecord.setTaskCode(taskCode);
        fileTaskExportRecord.setStatus(TaskStatusEnum.IN_LINE.getStatus());
        fileTaskExportRecord.setCreateBy(UserInfoUtils.getCurrentUserId());
        fileTaskRecordMapper.insert(fileTaskExportRecord);
        return fileTaskExportRecord.getId();
    }

    @Override
    public void updateById(FileTaskExportRecord fileTaskExportRecord) {
        fileTaskRecordMapper.updateById(fileTaskExportRecord);
    }

    @Override
    public void updateProcessingResultWithOptimisticLock(FileTaskExportRecord fileTaskExportRecord) {
        Wrapper<FileTaskExportRecord> updateWrapper = Wrappers.lambdaUpdate(FileTaskExportRecord.class)
                .eq(FileTaskExportRecord::getId, fileTaskExportRecord.getId())
                .eq(FileTaskExportRecord::getStatus, TaskStatusEnum.PROCESSING.getStatus());
        fileTaskRecordMapper.update(fileTaskExportRecord, updateWrapper);
    }

    @Override
    public PageResult<FileTaskExportRecord> getTaskRecordsByPage(PageRequest<TaskRecordsQueryParam> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), () -> {
            TaskRecordsQueryParam taskRecordsQueryParam = pageRequest.getRequestParam();
            Wrapper<FileTaskExportRecord> taskRecordWrapper = Wrappers.lambdaQuery(FileTaskExportRecord.class)
                    .eq(FileTaskExportRecord::getTaskCode, taskRecordsQueryParam.getTaskCode());
            return fileTaskRecordMapper.selectList(taskRecordWrapper);
        });

    }
}
