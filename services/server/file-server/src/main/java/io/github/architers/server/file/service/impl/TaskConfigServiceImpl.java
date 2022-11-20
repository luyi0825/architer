package io.github.architers.server.file.service.impl;

import io.github.architers.server.file.domain.entity.TaskConfig;
import io.github.architers.server.file.service.ITaskConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务配置对应的实现类
 *
 * @author luyi
 */
@Service
public class TaskConfigServiceImpl implements ITaskConfigService {
    @Override
    public List<TaskConfig> findByTaskCode(String taskCode) {
        List<TaskConfig> configs = new ArrayList<>();
        TaskConfig config = new TaskConfig();
        config.setLimitSecond(60L);
        config.setLimitCount(2);
        configs.add(config);
        return configs;
    }
}
