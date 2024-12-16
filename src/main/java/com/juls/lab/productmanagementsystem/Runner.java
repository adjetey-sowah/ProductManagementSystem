package com.juls.lab.productmanagementsystem;

import com.juls.lab.productmanagementsystem.exception.ResourceNotFoundException;
import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import com.juls.lab.productmanagementsystem.model.ProductAttribute;
import com.juls.lab.productmanagementsystem.repository.CategoryRepository;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.service.ProductService;
import com.juls.lab.productmanagementsystem.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {
    }


}
