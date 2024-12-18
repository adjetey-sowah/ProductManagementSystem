package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.data.model.Role;
import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.dto.RegistrationRequest;
import com.juls.lab.productmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl {

    private final UserRepository userRepository;

    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email: "+email));
    }

        public User registerUser(RegistrationRequest registrationRequest){
            var user = new User();
            user.setUsername(registrationRequest.username());
            user.setEmail(registrationRequest.email());
            user.setPassword(passwordEncoder().encode(registrationRequest.password()));
            user.setRole(Role.CUSTOMER);
            user.setEnabled(true);
            return this.userRepository.save(user);
        }

        public User registerAdminAndStoreManager(RegistrationRequest registrationRequest, Role role){
            var user = new User();
            user.setUsername(registrationRequest.username());
            user.setEmail(registrationRequest.email());
            user.setPassword(passwordEncoder().encode(registrationRequest.password()));
            user.setRole(role);
            user.setEnabled(true);
            return this.userRepository.save(user);
        }


        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

}
