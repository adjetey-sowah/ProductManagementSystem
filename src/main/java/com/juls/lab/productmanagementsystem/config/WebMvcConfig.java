package com.juls.lab.productmanagementsystem.config;

import com.juls.lab.productmanagementsystem.interceptor.LoggingInterceptor;
import com.juls.lab.productmanagementsystem.interceptor.PerformanceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;
    @Autowired
    private PerformanceInterceptor performanceInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
        registry.addInterceptor(performanceInterceptor).addPathPatterns("/**");
    }
}
