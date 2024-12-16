package com.juls.lab.productmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String password;

    @NaturalId(mutable = false)
    private String email;

    private Role role;

}
