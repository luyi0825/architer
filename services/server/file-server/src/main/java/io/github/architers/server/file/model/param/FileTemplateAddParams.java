package io.github.architers.server.file.model.param;

import io.github.architers.server.file.model.entity.FileTemplateCheckRowInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author luyi
 */
@Data
public class FileTemplateAddParams {

    /**
     * 模板编码
     */
    @NotBlank(message = "模板编码不能为空")
    private String templateCode;

    /**
     * 文件目录
     */
    @NotNull(message = "文件目录不能为空")
    private Long catalogId;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空")
    private String templateCaption;

    /**
     * 校验文件版本
     */
    @Valid
    @NotNull(message = "校验文件版本不能为空")
    private FileTemplateCheckFileVersionParam checkFileVersion;

    /**
     * 校验的行信息
     */
    @Valid
    @NotNull(message = "校验行信息不能为空")
    private FileTemplateCheckRowInfoParams checkRowInfo;

    /**
     * 备注信息
     */
    private String remark;




}
