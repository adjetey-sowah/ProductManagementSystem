package com.juls.lab.productmanagementsystem.config;

import com.juls.lab.productmanagementsystem.interceptors.AuthenticationInterceptors;
import com.juls.lab.productmanagementsystem.interceptors.LoggingInterceptor;
import com.juls.lab.productmanagementsystem.interceptors.OperationsInterceptor;
import com.juls.lab.productmanagementsystem.interceptors.PerformanceInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final OperationsInterceptor operationsInterceptor;
    private final AuthenticationInterceptors authenticationInterceptors;
    private final PerformanceInterceptor performanceInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(operationsInterceptor).addPathPatterns("/**");
        registry.addInterceptor(authenticationInterceptors).addPathPatterns("/**")
                .excludePathPatterns("/api/auth/**","/swagger-ui/**","/v3/api-docs/**");
        registry.addInterceptor(performanceInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
    }
}
