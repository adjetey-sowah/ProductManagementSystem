package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.dto.CategoryDTO;
import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import com.juls.lab.productmanagementsystem.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")

public class CategoryController {

    public final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Page<Category>> getAllCategories(@RequestParam(defaultValue = "0")int page,
                                                           @RequestParam(defaultValue = "10")int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Category> categories = this.categoryService.getAllCategories(pageable);
        return ResponseEntity.ok().body(categories);
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO category){
        Category newCategory = this.categoryService.createCategory(
                category.getCategoryName()
                ,category.getDescription()
                ,category.getParentId());
        return ResponseEntity.status(201).body(newCategory);
    }

    @GetMapping("/{categoryId}/subcategories")
    public ResponseEntity<Set<Category>> getSubCategories(@PathVariable Long categoryId) {
        Set<Category> subCategories = categoryService.getSubCategories(categoryId);
        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/categoryTree")
    public ResponseEntity<List<Category>> getCategoryTree(){
        List<Category> categoryTree = this.categoryService.getCategoryTree();
        return ResponseEntity.ok(categoryTree);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<Product>> productsUnderCategory(@PathVariable Long categoryId){
        List<Product> productList = this.categoryService.getProductsUnderCategory(categoryId);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId){
        Category category = this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/move-products")
    public ResponseEntity<String> moveProducts(
            @RequestParam Long sourceCategoryId,
            @RequestParam Long targetCategoryId) {

        try {
            categoryService.moveProducts(sourceCategoryId, targetCategoryId);
            return ResponseEntity.ok("Products moved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error moving products: " + e.getMessage());
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long categoryId,
            @RequestParam(required = false) Long reassignToCategoryId) {

        try {
            categoryService.deleteCategory(categoryId, reassignToCategoryId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Category deleted successfully.");
        } catch (Exception e) {
            // You could return a more specific error message or handle different exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting category: " + e.getMessage());
        }
    }

        // Update a category
        @PutMapping("/update/{categoryId}")
        public ResponseEntity<Category> updateCategory(
                @PathVariable Long categoryId,
                @RequestParam(required = false) String newName,
                @RequestParam(required = false) String newDescription,
                @RequestParam(required = false) Long newParentId) {

            try {
                Category updatedCategory = categoryService.updateCategory(categoryId, newName, newDescription, newParentId);
                return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
            } catch (IllegalArgumentException e) {
                // Handle case where parent category is not found
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } catch (Exception e) {
                // Handle any other exceptions
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }








}
