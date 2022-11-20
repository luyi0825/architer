package io.github.architers.server.file.service;

import io.github.architers.server.file.domain.entity.FileTask;

public interface ITaskNodeService {
    FileTask findByTaskCode(String taskCode);
}
