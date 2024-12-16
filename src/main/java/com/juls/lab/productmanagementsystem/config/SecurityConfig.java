package com.juls.lab.productmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**").permitAll()  // Allow everything under swagger-ui
                        .requestMatchers("/api/**").permitAll()  // API endpoints
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()  // Require authentication for other requests
                )
                .build();
    }


}
