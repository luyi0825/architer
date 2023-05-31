package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.mapper.FileTaskExportRecordMapper;
import io.github.architers.server.file.mapper.FileTaskImportRecordMapper;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FileTaskExportRecordServiceImpl extends ServiceImpl<FileTaskExportRecordMapper, FileTaskExportRecord> implements IFileTaskExportRecordService {
    @Override
    public PageResult<FileTaskExportRecord> getTaskRecordsByPage(PageRequest<TaskRecordsPageParam> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), () -> {
            TaskRecordsPageParam taskRecordsPageParam = pageRequest.getRequestParam();
            Wrapper<FileTaskExportRecord> taskRecordWrapper = Wrappers.lambdaQuery(FileTaskExportRecord.class)
                    .eq(StringUtils.hasText(taskRecordsPageParam.getTaskCode()), FileTaskExportRecord::getTaskCode, taskRecordsPageParam.getTaskCode())
                    .orderByDesc(FileTaskExportRecord::getId);
            return baseMapper.selectList(taskRecordWrapper);
        });

    }

    @Override
    public Long initTask(String taskCode) {
        FileTaskExportRecord fileTaskExportRecord = new FileTaskExportRecord();
        fileTaskExportRecord.setTaskCode(taskCode);
        fileTaskExportRecord.setStatus(TaskRecordStatusEnum.IN_LINE.getStatus());
        fileTaskExportRecord.setCreateBy(UserInfoUtils.getCurrentUserId());
        baseMapper.insert(fileTaskExportRecord);
        return fileTaskExportRecord.getId();
    }

    @Override
    public void updateProcessingResultWithOptimisticLock(FileTaskExportRecord fileTaskExportRecord) {
        Wrapper<FileTaskExportRecord> updateWrapper = Wrappers.lambdaUpdate(FileTaskExportRecord.class)
                .eq(FileTaskExportRecord::getId, fileTaskExportRecord.getId())
                .eq(FileTaskExportRecord::getStatus, TaskRecordStatusEnum.PROCESSING.getStatus());
        baseMapper.update(fileTaskExportRecord, updateWrapper);
    }

}
