package io.github.architers.common.jwttoken;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.architers.context.utils.JsonUtils;

import java.util.*;

public class JwtTokenUtils {

    private static String TOKEN_SECRET = "TOKEN_SECRET";

    /**
     * 生成签名，30分钟过期
     *
     * @param **userInfo** 用户信息 用户姓名
     * @param **other**    用户其他信息 用户id
     * @return
     */
    public static String sign(String username, String userInfo, long expire) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + expire);
            //私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    //jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
                    .withJWTId(UUID.randomUUID().toString())
                    //下发的时间
                    .withIssuedAt(new Date())
                    .withClaim("username", username)
                    .withClaim("userInfo", userInfo)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //未验证通过会抛出异常
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取info信息
     *
     * @param **token**
     * @return
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt().after(new Date());
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String userInfo(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userInfo").asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUserName("admin");
        userInfo.setUserCaption("管理员");
        List<UserInfo.RoleInfo> roleInfos = new ArrayList<>();
        roleInfos.add(new UserInfo.RoleInfo(1L, "1", "角色1"));
        roleInfos.add(new UserInfo.RoleInfo(2L, "2", "角色2"));
        roleInfos.add(new UserInfo.RoleInfo(3L, "3", "角色2"));
        roleInfos.add(new UserInfo.RoleInfo(4L, "4", "角色2"));
        roleInfos.add(new UserInfo.RoleInfo(5L, "5", "角色2"));
        roleInfos.add(new UserInfo.RoleInfo(6L, "6", "角色2"));
        roleInfos.add(new UserInfo.RoleInfo(7L, "7", "角色2"));
        roleInfos.add(new UserInfo.RoleInfo(8L, "8", "角色2"));
        userInfo.setPermissions(new HashSet<>(Arrays.asList("1", "2", "rerererere")));
        userInfo.setRoles(roleInfos);
        String str = JsonUtils.toJsonString(userInfo);
        String token = JwtTokenUtils.sign("1", str, 1000L);
        System.out.println(token);

        System.out.println(JwtTokenUtils.getUserName(token));

        System.out.println(JwtTokenUtils.userInfo(token));
        //是否过期
        System.out.println(JwtTokenUtils.isExpired(token));
    }
}
