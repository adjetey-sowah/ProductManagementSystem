package com.juls.lab.productmanagementsystem.data.model;

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

    @NaturalId(mutable = true)
    private String email;

    @Enumerated
    @Column(name = "role")
    private Role role;

    private boolean isEnabled = false;

}
