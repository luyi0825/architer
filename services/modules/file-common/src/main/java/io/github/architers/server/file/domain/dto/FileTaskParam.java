package io.github.architers.server.file.domain.dto;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 执行任务参数
 *
 * @author luyi
 */
public class FileTaskParam<T> {


    /**
     * 文件任务主键ID
     */
    private Long recordId;

    /**
     * 任务编码
     */
    @NotBlank(message = "任务编码不能为空")
    private String taskCode;

    /**
     * 执行参数
     */
    @NotNull(message = "执行参数不能为空")
    @Valid
    private T taskParam;

    public Long getRecordId() {
        return recordId;
    }

    public FileTaskParam<T> setRecordId(Long recordId) {
        this.recordId = recordId;
        return this;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public FileTaskParam<T> setTaskCode(String taskCode) {
        this.taskCode = taskCode;
        return this;
    }

    public T getTaskParam() {
        return taskParam;
    }

    public FileTaskParam<T> setTaskParam(T taskParam) {
        this.taskParam = taskParam;
        return this;
    }
}
