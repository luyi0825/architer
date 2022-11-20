package io.github.architers.server.file.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;

/**
 * 导入模板
 *
 * @author luyi
 */
@Data
@TableName("file_template")
public class Template extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 目录ID
     */
    private Integer catalogId;


    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateCaption;

    /**
     * 版本
     */
    private String version;

    /**
     * 存储路径
     */
    private String savePath;

    /**
     * 模板url
     */
    private String templateUrl;


}
