package io.github.architers.server.file.domain.dto;


import io.github.architers.server.file.enums.TaskStatusEnum;
import lombok.Data;

/**
 * 任务进度
 *
 * @author luyi
 */
@Data
public class TaskRecord {

    private Long id;
    /**
     * 任务状态
     *
     * @see TaskStatusEnum
     */
    private TaskStatusEnum status;

    /**
     * 总数量
     */
    private Integer totalNum;

    /**
     * 成功数量
     */
    private Integer successNum;

    /**
     * 结果地址
     */
    private String resultUrl;

    public Long getId() {
        return id;
    }

    public TaskRecord setId(Long id) {
        this.id = id;
        return this;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public TaskRecord setStatus(TaskStatusEnum status) {
        this.status = status;
        return this;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public TaskRecord setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
        return this;
    }

    public Integer getSuccessNum() {
        return successNum;
    }

    public TaskRecord setSuccessNum(Integer successNum) {
        this.successNum = successNum;
        return this;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public TaskRecord setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
        return this;
    }
}
