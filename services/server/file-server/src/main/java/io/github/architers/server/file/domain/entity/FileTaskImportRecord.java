package io.github.architers.server.file.domain.entity;

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
@TableName("file_task_import_record")
public class FileTaskImportRecord extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务编码
     */
    private String taskId;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 任务状态
     *
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
     * 源文件地址
     */
    private String sourceUrl;

    /**
     * 错误的url
     */
    private String errorUrl;

    /**
     * 备注信息
     */
    private String remark;


}
