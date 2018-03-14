package org.mel.generic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] matcherArray = new String[]{"/", "/login", "/oauth/authorize", "/h2-*/**", "/swagger-*/**", "/v2/**"};

        http.csrf().disable()
                .headers().frameOptions().disable();

        http
                .requestMatchers()
                .antMatchers(matcherArray)
                .and()
                .authorizeRequests()
                .antMatchers(matcherArray).permitAll()
                .anyRequest().authenticated() // /api/v1/**
                .and()
                .formLogin()
                .permitAll();

    }

}
