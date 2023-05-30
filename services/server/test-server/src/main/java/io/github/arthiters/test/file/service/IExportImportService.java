package io.github.arthiters.test.file.service;

import io.github.architers.server.file.domain.params.ExportTaskParam;
import io.github.architers.server.file.domain.params.ImportTaskParam;
import io.github.arthiters.test.file.domain.params.ExportUser;

import java.util.Map;

public interface IExportImportService {

    void importData(ImportTaskParam<Map<String, Object>> importTaskParam);
}
