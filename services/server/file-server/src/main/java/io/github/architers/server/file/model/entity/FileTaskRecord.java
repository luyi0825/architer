package io.github.architers.server.file.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;

/**
 * 任务进度
 *
 * @author luyi
 */
@Data
@TableName("file_task_record")
public class FileTaskRecord extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 任务状态
     * @see io.github.architers.server.file.enums.TaskStatusEnum
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

    /**
     * 备注信息
     */
    private String remark;



}
