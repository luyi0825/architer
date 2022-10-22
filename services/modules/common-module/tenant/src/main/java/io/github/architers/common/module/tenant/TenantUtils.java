package io.github.architers.common.module.tenant;

import io.github.architers.context.web.ServletUtils;

/**
 * @author luyi
 * 租户工具类
 */
public class TenantUtils {

    public static Integer getTenantId() {
        String tenantId = ServletUtils.header("tenant_id");

        return Integer.parseInt(tenantId);
    }




}
