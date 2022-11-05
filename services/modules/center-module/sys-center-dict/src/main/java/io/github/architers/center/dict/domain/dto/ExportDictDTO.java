package io.github.architers.center.dict.domain.dto;

import io.github.architers.common.module.tenant.TenantUtils;
import lombok.Data;

import java.util.Set;

/**
 * 导出字典的dto
 *
 * @author luyi
 */
@Data
public class ExportDictDTO {
    /**
     * 勾选的ID
     */
    private Set<Long> checkIds;
    /**
     * 数据字典编码
     */
    private String dictCode;

    /**
     * 数据字典名称
     */
    private String dictCaption;

    /**
     * 租户ID
     */
    private Integer tenantId;

}
