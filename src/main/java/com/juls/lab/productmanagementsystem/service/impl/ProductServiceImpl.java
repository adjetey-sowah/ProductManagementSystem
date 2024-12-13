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
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long productId) {
        this.productRepository.deleteById(productId);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void updateProductStock(Long productId, Integer quantity) {

    }

    @Override
    public boolean isProductInStock(Long id) {
        return false;
    }

    @Override
    public List<Product> getNewArrivals(int limit) {
        return List.of();
    }

    @Override
    public List<Product> getTopSellingProducts() {
        return List.of();
    }

    @Override
    public void addNewAttribute(Long productId, String name, String value) {

    }

    @Override
    public List<Product> getProductsByByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return List.of();
    }

    @Override
    public void deactivateProduct(Long id) {

    }

    @Override
    public void activateProduct(Long id) {

    }

    @Override
    public List<Product> getDiscountedProducts() {
        return List.of();
    }


}
