package io.github.architers.server.file.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 导入状态上报传输对象
 *
 * @author luyi
 */
@Data
public class ImportStatusReportDTO implements FileStatusReportDTO {
    /**
     * 请求ID
     */
    private String requestId;

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
     * 总数量
     */
    private String totalNum;

    /**
     * 成功数量
     */
    private Integer successNum;

    /**
     * 错误文件地址
     */
    private String errorUrl;

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
