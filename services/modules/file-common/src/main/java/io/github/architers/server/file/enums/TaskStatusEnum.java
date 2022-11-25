package io.github.architers.server.file.enums;

/**
 * 任务结果枚举
 * 0.已取消、1.排队中、2.处理中、3.处理完成、4.处理失败
 *
 * @author luyi
 */
public enum TaskStatusEnum {

    /**
     * 取消
     */
    CANCEL(0),
    /**
     * 排队中
     */
    IN_LINE(1),
    /**
     * 处理中
     */
    PROCESSING(2),

    /**
     * 完成
     */
    FINISHED(3),

    /**
     * 失败
     */
    FAIL(4);

    private final Integer status;

    TaskStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}



