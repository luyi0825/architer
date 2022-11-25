package io.github.architers.server.file.service;

import io.github.architers.server.file.domain.entity.TaskRecord;

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
     * @param taskRecord 需要更新的记录
     */
    void updateById(TaskRecord taskRecord);

    /**
     * 更新处理中的任务的结果(乐观锁)
     *
     * @param taskRecord 更新的数据
     */
    void updateProcessingResultWithOptimisticLock(TaskRecord taskRecord);
}
