package com.springboot.inventory.category.controller;

import com.springboot.inventory.category.service.CategoryService;
import com.springboot.inventory.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        List<Category> categoryList = categoryService.getAllCategories(); // 데이터베이스에서 카테고리 목록 가져오기
        model.addAttribute("categoryList", categoryList); // 모델에 카테고리 목록 추가
        return "supply"; // supply.html 템플릿 렌더링
    }

}
