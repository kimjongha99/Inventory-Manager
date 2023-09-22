package com.springboot.inventory.category.controller;

import com.springboot.inventory.category.service.CategoryService;
import com.springboot.inventory.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category-api")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categorylist")
    public ResponseEntity<List<?>> getCategoryList() {

        List<Category> categoryList = categoryService.getCategoryList().getData();

        return ResponseEntity.ok(categoryList);
    }

}
