package io.github.architers.gateway;

import com.alibaba.nacos.api.remote.response.Response;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.architers.common.jwttoken.JwtTokenUtils;
import io.github.architers.common.jwttoken.TenantInfo;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.context.exception.NoStackBusException;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class TokenFilter implements GlobalFilter {

    private static int refresh_token_code = 401001;
    private static String tokenHeaderKey = "authorization";
    public static String Bearer = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("请求接口:{}", path);
        if (path.startsWith("/public/")) {
            return chain.filter(exchange);
        }
        List<String> authorizations = exchange.getRequest().getHeaders().get(tokenHeaderKey);
        if (authorizations == null) {
            throw new NoStackBusException("请求有误");
        }
        String token = authorizations.get(0).replace(Bearer, "");
        if (!StringUtils.hasText(token)) {
            throw new NoStackBusException("token有误");
        }
        DecodedJWT jwt = JwtTokenUtils.decoded(token);

        long currentTime = System.currentTimeMillis();
        //判断token是否过期
        if (jwt.getExpiresAt().getTime() < currentTime) {
            UserInfo userInfo = JsonUtils.readValue(jwt.getClaim("userInfo").asString(),
                    UserInfo.class);
            //token自动延续
            TenantInfo tenantInfo = userInfo.getTenantInfo();
            if (tenantInfo == null) {
                throw new BusException("租户信息不存在");
            }
            if (!tenantInfo.isRefreshToken()) {
                throw new NoStackBusException("token已经过期");
            }
            //超过token最大的时间
            if ((currentTime - jwt.getIssuedAt().getTime()) > 60_1000L * tenantInfo.getTokenMaxTime()) {
                throw new NoStackBusException("token已经过期");
            }
            //返回刷新token的code,用户自动刷新token
            log.info("开始刷新token:{}", token);
            autoRefreshToken(exchange, jwt, userInfo);
            return chain.filter(exchange);
        }
        //说明token没有过期
        return chain.filter(exchange);
    }

    /**
     * 自动刷新token:延期10分钟
     */
    private void autoRefreshToken(ServerWebExchange exchange, DecodedJWT jwt, UserInfo userInfo) {

        String token = JwtTokenUtils.sign(userInfo, jwt.getIssuedAt(), 10 * 60_1000L);
        //将新的token传递给下游
        exchange.getRequest().mutate().header(tokenHeaderKey, Bearer + token).build();
        //通知前端token变化
        exchange.getResponse().getHeaders().add("token", token);
        log.info("结束刷新token:{}", token);
    }
}
