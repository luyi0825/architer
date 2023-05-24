package io.github.architers.server.file.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * 文件模板
 *
 * @author luyi
 */
@Data
public class FileTemplate {
    /**
     * 模板ID
     */
    private Long id;

    /**
     * 目录ID
     */
    private Long catalogId;

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
     * 校验行信息
     */
    private FileTemplateCheckRowInfo checkRowInfo;

    /**
     * 校验版本信息
     */
    private FileTemplateCheckFileVersion checkFileVersion;


    /**
     * 模板url
     */
    private String templateUrl;

    /**
     * 模板key
     */
    private String templateKey;

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
