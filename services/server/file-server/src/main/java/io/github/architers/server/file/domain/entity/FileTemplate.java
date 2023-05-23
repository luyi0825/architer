package io.github.architers.server.file.domain.entity;

import lombok.Data;

import java.util.Date;

/**
 * 文件模板
 * @author luyi
 */
@Data
public class FileTemplate {
    /**
     * 模板ID
     */
    private Integer id;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateCaption;

    /**
     * 模板md5字符串
     */
    private String md5;

    /**
     * 模板校验信息
     */
    private CheckInfo checkInfo;

    /**
     * 模板版本
     */
    private String version;

    /**
     * 目录ID
     */
    private Long catalogId;

    /**
     * 模板url
     */
    private String templateUrl;

    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedAt;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 是否删除: 0,否 1,是
     */
    private Integer isDelete;

    /**
     * 描述
     */
    private String remark;
}
