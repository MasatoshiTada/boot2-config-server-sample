package com.example.client.config;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final WebEndpointProperties webEndpointProperties;

    public SecurityConfig(WebEndpointProperties webEndpointProperties) {
        this.webEndpointProperties = webEndpointProperties;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // DO NOT use NoOpPasswordEncoder in production. Use BCryptPasswordEncoder instead.
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("actuator").password("password").roles("ACTUATOR").and()
                .withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(webEndpointProperties.getBasePath() + "/**").hasRole("ACTUATOR")
                .anyRequest().authenticated();

        // DO NOT disable CSRF in production. Before "POST /refresh", get CSRF token by some means.
        http.csrf().disable();
    }
}
