package io.github.architers.server.file.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;

/**
 * 任务进度
 *
 * @author luyi
 */
@Data
public class TaskRecord extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 任务状态:0.已取消、1.排队中、2.处理中、3.处理完成、5.处理失败
     */
    private Integer status;

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

}
