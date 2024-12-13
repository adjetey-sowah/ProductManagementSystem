package com.juls.lab.productmanagementsystem.service;

import com.juls.lab.productmanagementsystem.exception.ProductNotFoundException;
import com.juls.lab.productmanagementsystem.exception.ResourceNotFoundException;
import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService{

    Product createProduct(Product product);
    List<Product> getAllProducts();
    void deleteProduct(Long productId);
    Product updateProduct(Long id, Product product) throws ResourceNotFoundException;
    Product getProductById(Long id) throws ResourceNotFoundException;
    void updateProductStock(Long productId, Integer quantity) throws ResourceNotFoundException;
    boolean isProductInStock(Long id) throws ProductNotFoundException;
    List<Product> getNewArrivals(int limit);
    List<Product> getTopSellingProducts();
    void addNewAttribute(Long productId, String name, String value);
    List<Product> getProductsByByCategory(Long categoryId) throws ResourceNotFoundException;
    List<Product> searchProducts(String keyword);
    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);
    void deactivateProduct(Long id);
    void activateProduct(Long id);
    List<Product> getDiscountedProducts();






}
