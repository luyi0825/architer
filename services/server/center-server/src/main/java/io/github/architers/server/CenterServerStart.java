package io.github.architers.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.UUID;

/**
 * 中台启动类
 *
 * @author luyi
 */
@SpringBootApplication()
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
public class CenterServerStart {

    public static void main(String[] args) {
        new SpringApplication(CenterServerStart.class).run(args);
    }


    @PreAuthorize(value = "")
    @PostMapping("/test")
    public String test() {
        return UUID.randomUUID().toString();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.authorizeExchange(exchanges -> exchanges // 对于请求进行匹配
//                .pathMatchers("/*").permitAll()
//        );
        http.authorizeExchange().pathMatchers("/*").permitAll();
        //关闭表单校验
        http.formLogin().disable();
        http.httpBasic().disable();

        http.csrf(csrf -> csrf.disable().headers().disable()); // csrf 防护进行配置
        http.cors(cors -> cors.configurationSource( // 对跨域请求进行配置
                exchange -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Collections.singletonList("*"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setExposedHeaders(Collections.singletonList("Content-Disposition"));
                    config.setAllowCredentials(true);
                    config.applyPermitDefaultValues();
                    return config;
                }
        ));
        return http.build();
    }


}
