package com.juls.lab.productmanagementsystem.interceptors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class OperationsInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(OperationsInterceptor.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY; // Replace with your actual secret key

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String user = extractUserFromJwt(request);
        String operation = request.getRequestURI();
        String method = request.getMethod();


        logger.info("User: " + user + " | Operation: " + operation + " | Method: " + method + " | Timestamp: " + LocalDateTime.now());
        logToFile("User: " + user + " | Operation: " + operation + " | Method: " + method + " | Timestamp: " + LocalDateTime.now());
        return true; // Continue processing the request
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String user = extractUserFromJwt(request);
        String operation = request.getRequestURI();

        logger.info("User: " + user + " | Completed Operation: " + operation + " | Response Status: " + response.getStatus() + " | Timestamp: " + LocalDateTime.now());
        logToFile("User: " + user + " | Completed Operation: " + operation + " | Response Status: " + response.getStatus() + " | Timestamp: " + LocalDateTime.now());
    }

    private String extractUserFromJwt(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY.getBytes()) // Use the same key used to sign the JWT
                        .parseClaimsJws(token)
                        .getBody();
                return claims.getSubject(); // Typically, the subject contains the username or user ID
            }
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
        }
        return "Anonymous";
    }

    private void logToFile(String message) {
        try (FileWriter writer = new FileWriter("operations.log", true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
