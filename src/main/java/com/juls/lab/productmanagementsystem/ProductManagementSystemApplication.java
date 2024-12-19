package com.juls.lab.productmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.juls.lab.productmanagementsystem.repository")
public class ProductManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementSystemApplication.class, args);
    }

}
