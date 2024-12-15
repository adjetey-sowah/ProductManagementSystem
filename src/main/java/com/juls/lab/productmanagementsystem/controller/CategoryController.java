package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.dto.CategoryBody;
import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")

public class CategoryController {

    public final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categoryList = this.categoryService.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryBody category){
        Category newCategory = this.categoryService.createCategory(category.categoryName(), category.description(), category.parentId());
        return ResponseEntity.ok().body(newCategory);
    }

}
