package com.juls.lab.productmanagementsystem.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PerformanceInterceptor  implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);
    private static final long THRESHOLD_MS = 3000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        if (duration > THRESHOLD_MS) {
            logger.warn("Slow request detected! URL: {} | Duration: {}ms", request.getRequestURL(), duration);
        }
    }
}



