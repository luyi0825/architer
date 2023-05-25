package io.github.architers.server.file.model.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * 文件模板
 *
 * @author luyi
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName( autoResultMap = true,resultMap = "baseResultMap")
public class FileTemplate extends BaseEntity {
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
     * 校验行信息
     */
    @TableField( jdbcType = JdbcType.VARCHAR,  typeHandler =
            JacksonTypeHandler.class, insertStrategy = FieldStrategy.NOT_NULL,
            updateStrategy = FieldStrategy.NOT_NULL)
    private FileTemplateCheckRowInfo checkRowInfo;

    /**
     * 校验版本信息
     */
    @TableField( jdbcType = JdbcType.VARCHAR, typeHandler =
            JacksonTypeHandler.class, insertStrategy = FieldStrategy.NOT_NULL, updateStrategy = FieldStrategy.NOT_NULL)
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
     * 描述
     */
    private String remark;
}
