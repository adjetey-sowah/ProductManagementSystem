package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.exception.ProductNotFoundException;
import com.juls.lab.productmanagementsystem.exception.ResourceNotFoundException;
import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import com.juls.lab.productmanagementsystem.model.ProductAttribute;
import com.juls.lab.productmanagementsystem.repository.ProductRepository;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public final ProductRepository productRepository;
    public final CategoryService categoryService;


    @Override
    public Product createProduct(Product product) {
        Product savedProduct = this.productRepository.save(product);
        return savedProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @SneakyThrows
    @Override
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        this.productRepository.delete(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ResourceNotFoundException {
        Product existingProduct = getProductById(id);

        existingProduct.setProductName(product.getProductName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setQuantityInStock(product.getQuantityInStock());
        existingProduct.setActive(product.isActive());
        existingProduct.setProductAttribute(product.getProductAttribute());

        return productRepository.save(existingProduct);
    }

    @Override
    public Product getProductById(Long id) throws ResourceNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id "+ id));
    }

    @Override
    public void updateProductStock(Long productId, Integer quantity) {

    }

    @Override
    public boolean isProductInStock(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id "+id));
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


    @SneakyThrows
    @Override
    public void addNewAttribute(Long productId, String name, String value) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id "+productId+" not found"));
        ProductAttribute newAttribute = new ProductAttribute();
        newAttribute.setProduct(product);
        newAttribute.setName(name);
        newAttribute.setValue(value);

        product.getProductAttribute().add(newAttribute);
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByByCategory(Long categoryId) throws ResourceNotFoundException {
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() ->  new ResourceNotFoundException("Category not found with id: " +categoryId));
        return this.productRepository.findProductByCategory(category);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return this.productRepository.findByProductNameContainingIgnoreCaseAAndActiveTrue(keyword);
    }

    @Override
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return this.productRepository
                .findByPriceBetweenAndActiveTrue(minPrice, maxPrice);
    }

    @SneakyThrows
    @Override
    public void deactivateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    @SneakyThrows
    @Override
    public void activateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    public List<Product> getDiscountedProducts() {
        return productRepository.findByDiscountGreaterThanAndActiveTrue(BigDecimal.ZERO);
    }


}
