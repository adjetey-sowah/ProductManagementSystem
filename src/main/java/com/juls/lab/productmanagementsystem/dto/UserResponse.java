package com.juls.lab.productmanagementsystem.dto;

import com.juls.lab.productmanagementsystem.data.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private Role role;
}
