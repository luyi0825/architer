package io.github.architers.server.file;

import io.github.architers.server.file.domain.dto.ExportStatusReportDTO;
import io.github.architers.server.file.domain.dto.FileStatusReportDTO;

/**
 * 状态上报
 */
public interface StatusReport<T extends FileStatusReportDTO> {
    /**
     * 处理中
     */
    void processing(T fileStatusReport);

    /**
     * 完成
     */
    void finished(T fileStatusReport);

    /**
     * 失败
     */
    void failed(T fileStatusReport);

    /**
     * 取消
     */
    void cancel(T fileStatusReport);
}
