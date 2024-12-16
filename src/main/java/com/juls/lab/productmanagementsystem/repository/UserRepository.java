package com.juls.lab.productmanagementsystem.repository;

import com.juls.lab.productmanagementsystem.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
