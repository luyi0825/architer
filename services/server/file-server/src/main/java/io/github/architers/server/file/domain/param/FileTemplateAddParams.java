package io.github.architers.server.file.domain.param;

import io.github.architers.server.file.domain.entity.CheckInfo;
import lombok.Data;

/**
 * @author luyi
 */
@Data
public class FileTemplateAddParams {

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 文件目录
     */
    private String folder;

    /**
     * 模板名称
     */
    private String templateCaption;

    /**
     * 校验信息
     */
    private CheckInfo checkInfo;

}