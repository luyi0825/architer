package io.github.architers.server.file.service;

import io.github.architers.server.file.domain.dto.ExecuteTaskParam;

import java.util.Map;

/**
 * @author luyi
 */
public interface TaskService {


    boolean sendTask(ExecuteTaskParam executeTaskParam);

    void sendAsyncTask(String taskCode, Map<String, Object> executeParam);
}
