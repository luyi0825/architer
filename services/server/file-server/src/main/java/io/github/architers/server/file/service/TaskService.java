package io.github.architers.server.file.service;

import io.github.architers.server.file.domain.param.ExecuteTaskParam;

/**
 * @author luyi
 */
public interface TaskService {


    boolean sendTask(ExecuteTaskParam executeTaskParam);
}
