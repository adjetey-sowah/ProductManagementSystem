package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import com.juls.lab.productmanagementsystem.repository.CategoryRepository;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public Category createCategory(String name, String description, Long parentId) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        if(parentId != null){
            Category parent = this.categoryRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            category.setParent(parent);
            parent.getSubCategories().add(category);
        }
        return this.categoryRepository.save(category);
    }
    @Override
    public void deleteCategory(Long categoryId, Long reassignToCategoryId) {
        Category category = getCategoryById(reassignToCategoryId);
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public Set<Category> getSubCategories(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return category.getSubCategories();
    }

    @Override
    public List<Product> getProductsUnderCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public void moveProducts(Long sourceCategoryId, Long targetCategoryId) {

    }

    @Override
    public List<Category> getCategoryTree() {
        return List.of();
    }
}
