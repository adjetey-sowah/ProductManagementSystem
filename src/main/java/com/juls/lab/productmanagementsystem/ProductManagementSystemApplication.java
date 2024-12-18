package com.juls.lab.productmanagementsystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductManagementSystemApplication {

    public static void main(String[] args) {

        // Load .env file into the environment
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Specify the directory of the .env file
                .load();

        // Optional: Print a variable to confirm it was loaded
        System.out.println("MongoDB URL: " + dotenv.get("MONGO_DB_URL"));

        SpringApplication.run(ProductManagementSystemApplication.class, args);

    }

}
