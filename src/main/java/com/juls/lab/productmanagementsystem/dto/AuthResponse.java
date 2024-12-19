package com.juls.lab.productmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private String token;
    private String refreshToken;
    private UserResponse user;
}
