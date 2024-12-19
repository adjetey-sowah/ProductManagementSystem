package com.juls.lab.productmanagementsystem.service.impl;

import com.juls.lab.productmanagementsystem.exception.ProductNotFoundException;
import com.juls.lab.productmanagementsystem.data.model.Category;
import com.juls.lab.productmanagementsystem.data.model.Product;
import com.juls.lab.productmanagementsystem.repository.CategoryRepository;
import com.juls.lab.productmanagementsystem.service.CategoryService;
import com.juls.lab.productmanagementsystem.util.ProductCategoryBinaryTree;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductCategoryBinaryTree binaryTree;

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
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
        binaryTree.insertCategory(category);
        return this.categoryRepository.save(category);
    }


    @Override
        public void deleteCategory(Long categoryId, Long reassignToCategoryId) {
            Category categoryToDelete = getCategoryById(categoryId);

            if (reassignToCategoryId != null) {
                Category reassignToCategory = getCategoryById(reassignToCategoryId);

                // Reassign products to the new category
                for (Product product : categoryToDelete.getProducts()) {
                    product.setCategory(reassignToCategory);
                    reassignToCategory.getProducts().add(product);
                }
            } else {
                // If no reassignment, just remove products from the category
                for (Product product : categoryToDelete.getProducts()) {
                    product.setCategory(null);
                }
            }
            // Remove from parent category if exists
            if (categoryToDelete.getParent() != null) {
                categoryToDelete.getParent().getSubCategories().remove(categoryToDelete);
            }
            // Delete the category and update binary tree
            categoryRepository.delete(categoryToDelete);
            binaryTree.removeCategory(categoryToDelete.getName());
    }

    @Override
    public Category updateCategory(Long categoryId, String newName, String newDescription, Long newParentId){
        Category category = this.getCategoryById(categoryId);

        if(newName != null){
            category.setName(newName);
        }
        if(newDescription != null){
            category.setDescription(newDescription);
        }
        // Update parent category if needed
        if (newParentId != null && !newParentId.equals(category.getParent() != null ? category.getParent().getId() : null)) {
            Category newParent = categoryRepository.findById(newParentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            category.setParent(newParent);
        }
        Category updatedCategory = this.categoryRepository.save(category);
        binaryTree.insertCategory(updatedCategory);
        return updatedCategory;
    }

    @Override
    public Set<Category> getSubCategories(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return category.getSubCategories();
    }

    @Override
    public List<Product> getProductsUnderCategory(Long categoryId) {
        // Retrieve the category from the binary tree for fast access
        Category category = getCategoryById(categoryId);
        // Use BinaryTree for hierarchical retrieval
        return binaryTree.getProductsUnderCategory(category.getName());
    }

    @Override
    public void moveProducts(Long sourceCategoryId, Long targetCategoryId) {
        Category sourceCategory = getCategoryById(sourceCategoryId);
        Category targetCategory = getCategoryById(targetCategoryId);

        // Move all products from source to target
        for (Product product : sourceCategory.getProducts()) {
            product.setCategory(targetCategory);
            targetCategory.getProducts().add(product);
        }
        // Clear products in the source category
        sourceCategory.getProducts().clear();
        sourceCategory.setUpdatedAt(LocalDateTime.now());
        targetCategory.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(sourceCategory);
        categoryRepository.save(targetCategory);
    }

    @Override
    public List<Category> getCategoryTree() {
        List<Category> allCategories = categoryRepository.findAll();
        // Organize into a tree structure
        Map<Long, Category> categoryMap = allCategories.stream()
                .collect(Collectors.toMap(Category::getId, category -> category));
        List<Category> roots = new ArrayList<>();
        for (Category category : allCategories) {
            if (category.getParent() == null) {
                roots.add(category);
            } else {
                categoryMap.get(category.getParent().getId()).getSubCategories().add(category);
            }
        }

        return roots;
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryByName(String categoryName) throws ProductNotFoundException {
        return this.categoryRepository.findCategoryByNameContainingIgnoreCase(categoryName)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }


    @PostConstruct
    private void loadBinaryTree() {
        List<Category> categories = this.categoryRepository.findAll();

        if (!categories.isEmpty()) {
            categories.forEach(binaryTree::insertCategory);
            System.out.println(categories.size()+" categories inserted into binary tree");
        } else {
            // Optionally, you can log or handle the case where categories are empty or null
            System.out.println("No categories found to insert into the binary tree.");
        }
    }

}
