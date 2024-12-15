package com.juls.lab.productmanagementsystem.service;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface CategoryService {

    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category createCategory(String name, String description, Long parentId);

    void deleteCategory(Long categoryId, Long reassignToCategoryId);

    Category updateCategory(Long categoryId, String newName, String newDescription, Long newParentId);
    Set<Category> getSubCategories(Long categoryId);
    List<Product> getProductsUnderCategory(Long categoryId);
    void moveProducts(Long sourceCategoryId, Long targetCategoryId);
    List<Category> getCategoryTree();


}
