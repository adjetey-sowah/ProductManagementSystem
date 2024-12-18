package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.dto.UserResponse;
import com.juls.lab.productmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {

    private UserRepository userRepository;

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email: "+email));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserResponse getUserById(Long id){
        return userRepository.findById(id)
                .map(this::mapToUserResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }


}
