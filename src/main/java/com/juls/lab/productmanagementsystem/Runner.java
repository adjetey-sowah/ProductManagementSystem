package com.juls.lab.productmanagementsystem;

import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
    }


}
