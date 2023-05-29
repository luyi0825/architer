package io.github.arthiters.test.file.service;

import io.github.architers.server.file.domain.params.ExportTaskParam;
import io.github.arthiters.test.file.domain.params.ExportUser;

public interface IExportUserService {
    void exportUse(ExportTaskParam<ExportUser> exportTaskParam);
}
