package com.juls.lab.productmanagementsystem.service;

import com.juls.lab.productmanagementsystem.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    Category createCategory(Category category);
    void deleteCategory(Long categoryId);
    Category updateCategory(Category category);


}
