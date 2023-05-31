package io.github.architers.server.file.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("file_task_import_record")
public class FileTaskImportRecord extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;


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

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


}
