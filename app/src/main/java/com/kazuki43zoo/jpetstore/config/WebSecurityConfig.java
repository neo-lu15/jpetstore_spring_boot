package com.kazuki43zoo.jpetstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .oauth2Client()
            .and()
            .oauth2Login()
            .tokenEndpoint()
            .and()
            .userInfoEndpoint()
            .and() // Add this line to close the userInfoEndpoint() configuration
            .permitAll() // Allow access to userInfo endpoint without authentication
            .defaultSuccessUrl("/catalog");

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http
            .authorizeRequests()
            .antMatchers("/my/**").permitAll() // Permit access to /my/** without authentication
            .anyRequest()
            .fullyAuthenticated()
            .and()
            .logout()
            .logoutSuccessUrl("/catalog")
            .deleteCookies("JSESSIONID")
            .permitAll();

        return http.build();
    }
}

