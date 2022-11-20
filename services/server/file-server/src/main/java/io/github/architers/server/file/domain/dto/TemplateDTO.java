package io.github.architers.server.file.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author luyi
 */
@Data
public class TemplateDTO {

    @NotNull(message = "模板id不能为空", groups = EditGroup.class)
    private String id;

    /**
     * 目录ID
     */
    @NotNull(message = "模板目录id不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer catalogId;

    /**
     * 模板编码
     */
    @NotNull(message = "模板编码不能为空", groups = {AddGroup.class})
    private String templateCode;

    /**
     * 模板名称
     */
    @NotNull(message = "模板名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String templateCaption;

    /**
     * 存储路径
     */
    @NotBlank(message = "存储路径不能为空", groups = {AddGroup.class})
    private String savePath;

}
