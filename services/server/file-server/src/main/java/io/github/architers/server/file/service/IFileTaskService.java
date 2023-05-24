package io.github.architers.server.file.service;

import io.github.architers.server.file.model.entity.FileTask;

public interface IFileTaskService {
    FileTask findByTaskCode(String taskCode);


}
