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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    public final ProductRepository productRepository;
    public final CategoryService categoryService;


    @Override
    public Product createProduct(Product product) {
        product.setActive(true);
        return this.productRepository.save(product);
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
        existingProduct.setDescription(product.getDescription());
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
    public void updateProductStock(Long productId, Integer quantity) throws ResourceNotFoundException {
        Product product = getProductById(productId);
        product.setQuantityInStock(product.getQuantityInStock() + quantity);
        productRepository.save(product);
    }

    @Override
    public boolean isProductInStock(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id "+id));
        return product.getQuantityInStock()>0;
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
        Category category = categoryService.getCategoryById(categoryId);
        Set<Product> products = new HashSet<>(); // Using Set to avoid duplicates
        collectProductsRecursively(category, products);
        return new ArrayList<>(products);
    }

    private void collectProductsRecursively(Category category, Set<Product> products) {
        // Add products from current category
        products.addAll(productRepository.findProductByCategory(category));

        // Recursively add products from all subcategories
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            for (Category subCategory : category.getSubCategories()) {
                collectProductsRecursively(subCategory, products);
            }
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return this.productRepository
                .findByProductNameContainingIgnoreCase(keyword);
    }


        @Override
        public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
            // Validate price range
            if (minPrice < 0 || maxPrice < 0) {
                throw new IllegalArgumentException("Price values cannot be negative");
            }

            if (minPrice > maxPrice) {
                throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
            }

            // Using JPA repository to fetch products within price range
            return productRepository.findByPriceBetweenOrderByPriceAsc(minPrice, maxPrice);
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
        return productRepository
                .findByDiscountGreaterThan(BigDecimal.ZERO);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> products = this.productRepository.findAll(pageable);
        return products;
    }


    public void moveProductToCategory(Long productId, Long targetCategoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Category targetCategory = categoryService.getCategoryById(targetCategoryId);
        // Move the product to the target category
        product.setCategory(targetCategory);

        // Update the product's category in the database
        productRepository.save(product);

        // Optionally, update the target category's updatedAt timestamp
        targetCategory.setUpdatedAt(LocalDateTime.now());
        categoryService.createCategory(targetCategory.getName(),targetCategory.getDescription(),targetCategory.getParent().getId());
    }

}
