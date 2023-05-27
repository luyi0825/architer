package io.github.architers.server.file.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.architers.context.autocode.BaseEntity;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;

/**
 * 任务进度
 *
 * @author luyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "file_task_export_record",autoResultMap = true)
public class FileTaskExportRecord extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务请求ID
     */
    private String requestId;

    /**
     * 任务编码
     */
    private String taskCode;
    /**
     * 请求参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> requestBody;

    /**
     * 任务状态
     *
     * @see TaskRecordStatusEnum
     */
    private Integer status;

    /**
     * 导出的文件名
     */
    private String fileName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

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
