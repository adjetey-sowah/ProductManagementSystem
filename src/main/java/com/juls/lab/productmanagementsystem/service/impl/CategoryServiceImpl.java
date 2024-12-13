package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.service.CategoryService;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public Category createCategory(Category category) {
        return null;
    }
    @Override
    public void deleteCategory(Long categoryId) {

    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }
}
