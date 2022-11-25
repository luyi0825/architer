package io.github.architers.server.file.service.impl;

import io.github.architers.server.file.domain.entity.FileTaskConfig;
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
    public List<FileTaskConfig> findByTaskCode(String taskCode) {
        List<FileTaskConfig> configs = new ArrayList<>();
        FileTaskConfig config = new FileTaskConfig();
        config.setLimitSecond(60L);
        config.setLimitCount(2);
        configs.add(config);
        return configs;
    }
}
