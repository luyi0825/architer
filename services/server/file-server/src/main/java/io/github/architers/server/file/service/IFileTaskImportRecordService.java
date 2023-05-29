package io.github.architers.server.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.eums.TransactionMessageResult;
import io.github.architers.server.file.mapper.FileTaskImportRecordMapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author luyi
 */
public interface IFileTaskImportRecordService extends IService<FileTaskImportRecord> {


    PageResult<FileTaskImportRecord> getImportRecordsByPage(PageRequest<TaskRecordsPageParam> pageRequest);


}
