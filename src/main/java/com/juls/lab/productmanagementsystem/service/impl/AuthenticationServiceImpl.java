package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.Role;
import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.dto.AuthResponse;
import com.juls.lab.productmanagementsystem.dto.LoginRequest;
import com.juls.lab.productmanagementsystem.dto.SignUpRequest;
import com.juls.lab.productmanagementsystem.dto.UserResponse;
import com.juls.lab.productmanagementsystem.repository.UserRepository;
import com.juls.lab.productmanagementsystem.security.UserDetailsServiceImpl;
import com.juls.lab.productmanagementsystem.security.jwt.JWTService;
import com.juls.lab.productmanagementsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;



    @Override
    public AuthResponse register(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        var user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUsername(request.getUsername());
        user.setRole(request.getRole() != null ? request.getRole() : Role.CUSTOMER);

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .user(mapToUserResponse(user))
                .build();
    }

<<<<<<< Updated upstream
    @Override
    public AuthResponse authenticate(LoginRequest request) {
        System.out.println(request.getEmail() + " " + request.getPassword());

        User user = this.userService.getUserByEmail(request.getEmail());

        // Check if the user is found
        if (user == null) {
            log.error("User not found for email: {}", request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        log.info("User Role = {}", user.getRole());
        log.info("Authenticating user: {}", request.getEmail());

        // Check password match
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Password mismatch for user: {}", request.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        System.out.println("Done authenticating");
=======

    @Override
    public AuthResponse authenticate(LoginRequest request){
        log.info("Authenticating user {}",request);


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = this.userService.getUserByEmail(request.getEmail());

        log.info("I am done authenticating the user");
>>>>>>> Stashed changes
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthResponse.builder()
                .token(jwt)
                .refreshToken(refreshToken)
                .user(mapToUserResponse(user))
                .build();
    }

    @Override
    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
