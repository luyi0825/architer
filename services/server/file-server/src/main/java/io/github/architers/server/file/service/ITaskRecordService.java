package io.github.architers.server.file.service;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;

/**
 * @author Administrator
 */
public interface ITaskRecordService {

    /**
     * 初始化任务
     *
     * @param taskCode 任务编码
     * @return 任务的主键ID
     */
    Long initTask(String taskCode);

    /**
     * 通过ID更新
     *
     * @param fileTaskExportRecord 需要更新的记录
     */
    void updateById(FileTaskExportRecord fileTaskExportRecord);

    /**
     * 更新处理中的任务的结果(乐观锁)
     *
     * @param fileTaskExportRecord 更新的数据
     */
    void updateProcessingResultWithOptimisticLock(FileTaskExportRecord fileTaskExportRecord);

    PageResult<FileTaskExportRecord> getTaskRecordsByPage(PageRequest<TaskRecordsPageParam> pageRequest);
}
