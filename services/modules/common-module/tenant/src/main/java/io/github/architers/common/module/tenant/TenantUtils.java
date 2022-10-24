package io.github.architers.common.module.tenant;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luyi
 * 租户工具类
 */
public class TenantUtils {

    public static Integer getTenantId() {
        HttpServletRequest httpServletRequest = request();
        String tenantId = httpServletRequest.getHeader("tenant_id");

        return Integer.parseInt(tenantId);
    }

    public static HttpServletRequest request() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        return attributes.getRequest();
    }




}
