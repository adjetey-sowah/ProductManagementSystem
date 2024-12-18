package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.dto.ProductDTO;
import com.juls.lab.productmanagementsystem.exception.ProductNotFoundException;
import com.juls.lab.productmanagementsystem.exception.ResourceNotFoundException;
import com.juls.lab.productmanagementsystem.data.model.Category;
import com.juls.lab.productmanagementsystem.data.model.Product;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Product management API")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping("/all")
    @Operation(summary = "Get all products with pagination")
    public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0")int page,
                                                        @RequestParam(defaultValue = "10")int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = this.productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<List<Product>> getProductsByCategory(
            @PathVariable Long categoryId) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.getProductsByByCategory(categoryId));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by keyword")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate product")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateProduct(@PathVariable Long id) {
        productService.deactivateProduct(id);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate product")
    @ResponseStatus(HttpStatus.OK)
    public void activateProduct(@PathVariable Long id) {
        productService.activateProduct(id);
    }

    @PostMapping("/{id}/attributes")
    @Operation(summary = "Add new attribute to product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addNewAttribute(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String value) {
        productService.addNewAttribute(id, name, value);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock")
    public ResponseEntity<Void> updateProductStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) throws ResourceNotFoundException {
        productService.updateProductStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/stock")
    @Operation(summary = "Check if product is in stock")
    public ResponseEntity<Boolean> isProductInStock(@PathVariable Long id) throws ProductNotFoundException, ProductNotFoundException {
        return ResponseEntity.ok(productService.isProductInStock(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) throws ResourceNotFoundException {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }


    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO product) throws ProductNotFoundException {
        Product newProduct = new Product();
        newProduct.setProductName(product.getProductName());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantityInStock(product.getQuantityInStock());
        Category category = this.categoryService.getCategoryById(product.getCategoryId());
        newProduct.setDiscount(BigDecimal.valueOf(product.getDiscount()));
        newProduct.setCategory(category);
        return new ResponseEntity<>(productService.createProduct(newProduct), HttpStatus.CREATED);
    }





}
