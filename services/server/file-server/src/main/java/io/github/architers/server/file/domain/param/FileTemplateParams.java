package io.github.architers.server.file.domain.param;

import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author luyi
 */
@Data
public class FileTemplateParams {

    /**
     * 模板编码
     */
    @NotBlank(message = "模板编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String templateCode;

    /**
     * 文件目录
     */
    @NotNull(message = "文件目录不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long catalogId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String templateCaption;

    /**
     * 校验的行信息
     */
    @Valid
    @NotNull(message = "校验行信息不能为空", groups = {AddGroup.class, EditGroup.class})
    private FileTemplateCheckRowInfoParams checkRowInfo;

    /**
     * 备注信息
     */
    @Size(max = 50, message = "备注信息字符长度不能超过50", groups = {AddGroup.class, EditGroup.class})
    private String remark;


}
