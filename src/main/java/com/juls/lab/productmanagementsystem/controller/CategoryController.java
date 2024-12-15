package com.juls.lab.productmanagementsystem.controller;

import com.juls.lab.productmanagementsystem.model.Category;
import com.juls.lab.productmanagementsystem.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
