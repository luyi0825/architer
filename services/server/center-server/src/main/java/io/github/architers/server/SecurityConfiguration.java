package io.github.architers.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
public class SecurityConfiguration  {
    @Bean
    public DefaultSecurityFilterChain DefaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.formLogin().disable().csrf().disable().build();
    }

}
