package org.mel.generic.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private GenericUserDetailsService userDetailsService;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManager)
            .userDetailsService(userDetailsService);
    }


}
