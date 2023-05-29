package io.github.architers.server.file.service;

import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.eums.TransactionMessageResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author luyi
 */
public interface TaskService {


    boolean sendTask(ExecuteTaskParam executeTaskParam);

    TransactionMessageResult executeExportTask(ExecuteTaskParam executeTaskParam);

    TransactionMessageResult executeImportTask(ExecuteTaskParam executeTaskParam, MultipartFile file);
}
