package com.juls.lab.productmanagementsystem.service;

import com.juls.lab.productmanagementsystem.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

}
