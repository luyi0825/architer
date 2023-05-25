package io.github.architers.common.jwttoken;

import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ServletUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author luyi
 */
public class UserInfoUtils {
    public static UserInfo getUserInfo() {
        String authorization = ServletUtils.header("authorization");
        if (StringUtils.isNotEmpty(authorization) && authorization.startsWith("Bearer")) {
            String token = authorization.replace("Bearer ", "");
            String userInfo = JwtTokenUtils.userInfo(token);
            return JsonUtils.readValue(userInfo, UserInfo.class);
        }
        throw new RuntimeException("用户信息为空");
    }

    /**
     * 得到当前用户ID
     *
     * @return 用户主键ID
     */
    public static Long getCurrentUserId() {
        return 1L;
    }

    public static String getJwtToken() {
        String authorization = ServletUtils.header("authorization");
        if (StringUtils.isNotEmpty(authorization) && authorization.startsWith("Bearer")) {
            return authorization.replace("Bearer ", "");
        }
        throw new RuntimeException("token为空");
    }
}
