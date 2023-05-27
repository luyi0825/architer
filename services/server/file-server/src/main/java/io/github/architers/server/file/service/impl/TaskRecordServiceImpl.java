package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.mapper.FileTaskRecordMapper;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.service.ITaskRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        fileTaskExportRecord.setStatus(TaskRecordStatusEnum.IN_LINE.getStatus());
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
                .eq(FileTaskExportRecord::getStatus, TaskRecordStatusEnum.PROCESSING.getStatus());
        fileTaskRecordMapper.update(fileTaskExportRecord, updateWrapper);
    }

    @Override
    public PageResult<FileTaskExportRecord> getTaskRecordsByPage(PageRequest<TaskRecordsPageParam> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), () -> {
            TaskRecordsPageParam taskRecordsPageParam = pageRequest.getRequestParam();
            Wrapper<FileTaskExportRecord> taskRecordWrapper = Wrappers.lambdaQuery(FileTaskExportRecord.class)
                    .eq(StringUtils.hasText(taskRecordsPageParam.getTaskCode()), FileTaskExportRecord::getTaskCode, taskRecordsPageParam.getTaskCode())
                    .orderByDesc(FileTaskExportRecord::getId);
            return fileTaskRecordMapper.selectList(taskRecordWrapper);
        });

    }
}
