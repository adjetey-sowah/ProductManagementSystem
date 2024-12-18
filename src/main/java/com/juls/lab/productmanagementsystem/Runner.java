package com.juls.lab.productmanagementsystem;

import com.juls.lab.productmanagementsystem.data.model.User;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.service.ProductService;
import com.juls.lab.productmanagementsystem.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

   private final UserServiceImpl userService;

    @Override
    public void run(String... args) throws Exception {

        User user = this.userService.getUserByEmail("grace@gmail.com");
        System.out.println(user);

    }


}
