package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.dto.AuthResponse;
import com.juls.lab.productmanagementsystem.dto.LoginRequest;
import com.juls.lab.productmanagementsystem.dto.SignUpRequest;
import com.juls.lab.productmanagementsystem.dto.UserResponse;
import com.juls.lab.productmanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public AuthResponse register(SignUpRequest signUpRequest) {
        return null;
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        return null;
    }

    @Override
    public UserResponse mapToUserResponse(User user) {
        return null;
    }
}
