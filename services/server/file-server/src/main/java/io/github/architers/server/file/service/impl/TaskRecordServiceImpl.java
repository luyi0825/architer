package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.mapper.FileTaskRecordDao;
import io.github.architers.server.file.domain.dto.TaskRecordsQueryDTO;
import io.github.architers.server.file.domain.entity.FileTaskRecord;
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
    private FileTaskRecordDao fileTaskRecordDao;

    @Override
    public Long initTask(String taskCode) {
        FileTaskRecord fileTaskRecord = new FileTaskRecord();
        fileTaskRecord.setTaskCode(taskCode);
        fileTaskRecord.setStatus(TaskStatusEnum.IN_LINE.getStatus());
        fileTaskRecord.setCreateBy(UserInfoUtils.getCurrentUserId());
        fileTaskRecordDao.insert(fileTaskRecord);
        return fileTaskRecord.getId();
    }

    @Override
    public void updateById(FileTaskRecord fileTaskRecord) {
        fileTaskRecordDao.updateById(fileTaskRecord);
    }

    @Override
    public void updateProcessingResultWithOptimisticLock(FileTaskRecord fileTaskRecord) {
        Wrapper<FileTaskRecord> updateWrapper = Wrappers.lambdaUpdate(FileTaskRecord.class)
                .eq(FileTaskRecord::getId, fileTaskRecord.getId())
                .eq(FileTaskRecord::getStatus, TaskStatusEnum.PROCESSING.getStatus());
        fileTaskRecordDao.update(fileTaskRecord, updateWrapper);
    }

    @Override
    public PageResult<FileTaskRecord> getTaskRecordsByPage(PageRequest<TaskRecordsQueryDTO> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), () -> {
            TaskRecordsQueryDTO taskRecordsQueryDTO = pageRequest.getRequestParam();
            Wrapper<FileTaskRecord> taskRecordWrapper = Wrappers.lambdaQuery(FileTaskRecord.class)
                    .eq(FileTaskRecord::getTaskCode, taskRecordsQueryDTO.getTaskCode());
            return fileTaskRecordDao.selectList(taskRecordWrapper);
        });

    }
}
