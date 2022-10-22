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
        return null;
    }
}
