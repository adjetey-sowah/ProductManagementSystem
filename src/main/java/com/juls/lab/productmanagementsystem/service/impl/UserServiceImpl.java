package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {

    private UserRepository userRepository;

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email: "+email));
    }


}
