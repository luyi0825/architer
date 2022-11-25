package io.github.architers.server.file.service;

import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.server.file.domain.entity.FileTaskConfig;

import java.util.List;

public interface ITaskConfigService {

    /**
     * 通过任务编码查询任务配置
     *
     * @param taskCode 任务编码
     * @return 任务配置信息
     */
    @Cacheable(cacheName = "'taskConfig'", key = "#taskCode")
    List<FileTaskConfig> findByTaskCode(String taskCode);
}
