package io.github.architers.server.file.domain.param;

import io.github.architers.server.file.domain.entity.CheckInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
     * 校验信息
     */
    @NotNull(message = "校验信息不能为空")
    @Valid
    private CheckInfo checkInfo;

}
