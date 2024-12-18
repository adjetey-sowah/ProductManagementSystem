package com.juls.lab.productmanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableConfigurationProperties
public class AppConfig {

    @Value("${app.cache.size:100}")
    private int cacheSize;

    @Bean
    @Profile("prod")
    public CacheManager developmentCacheManager(){
            return new SimpleCacheManager();
    }

    @Bean
    @Profile("prod")
    public CacheManager productionCacheManager(){
            return new SimpleCacheManager();
}

    @Bean
    @ConfigurationProperties(prefix = "app.security")
    public SecurityProperties securityProperties(){
        return new SecurityProperties();
    }

}
