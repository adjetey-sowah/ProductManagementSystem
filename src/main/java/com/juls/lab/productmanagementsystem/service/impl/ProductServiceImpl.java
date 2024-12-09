package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.model.Product;
import com.juls.lab.productmanagementsystem.repository.ProductRepository;
import com.juls.lab.productmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
