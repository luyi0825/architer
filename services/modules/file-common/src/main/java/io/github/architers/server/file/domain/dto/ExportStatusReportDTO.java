package io.github.architers.server.file.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 导出状态上报的参数
 */
@Data
public class ExportStatusReportDTO implements FileStatusReportDTO {

    /**
     * 请求记录ID
     */
    private Long recordId;

    /**
     * 任务编码
     */
    private String taskCode;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 导出的文件名
     */
    private String fileName;

    /**
     * 成功数量
     */
    private Integer successNum;

    /**
     * 导出的地址
     */
    private String resultUrl;

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

    /**
     * 备注
     */
    private String remark;

}
