package com.juls.lab.productmanagementsystem.service;

import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.dto.AuthResponse;
import com.juls.lab.productmanagementsystem.dto.LoginRequest;
import com.juls.lab.productmanagementsystem.dto.SignUpRequest;
import com.juls.lab.productmanagementsystem.dto.UserResponse;

public interface AuthenticationService {

    AuthResponse register(SignUpRequest signUpRequest);
    AuthResponse authenticate(LoginRequest request);
    UserResponse mapToUserResponse(User user);
}
