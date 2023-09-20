package com.springboot.inventory.category.controller;

import com.springboot.inventory.category.dto.categoryServiceDto;
import com.springboot.inventory.category.service.CategoryService;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        List<Category> categoryList = categoryService.getAllCategories(); // 데이터베이스에서 카테고리 목록 가져오기
        model.addAttribute("categoryList", categoryList); // 모델에 카테고리 목록 추가
        return "supplyCreate"; // supplyCreate.html 템플릿 렌더링
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String categoryName, @RequestParam LargeCategory largeCategory, RedirectAttributes redirectAttributes) {
        categoryServiceDto category = categoryService.saveCategory(categoryName, largeCategory);

        return "redirect:/category/categories";  // 카테고리 목록 페이지로 리디렉션
    }




    @GetMapping("/categories")
    public String getCategoriesByLargeCategory(@RequestParam(name = "largeCategory",defaultValue = "COMPUTER") LargeCategory largeCategory, Model model) {
        if (largeCategory == null) {
            // 기본 동작 처리 (예: 기본 카테고리 설정 또는 오류 페이지로 리디렉션)
            return "redirect:/supply/list";
        }
        List<categoryServiceDto> categories = categoryService.getCategoriesByLargeCategory(largeCategory);
        model.addAttribute("selectedCategory", largeCategory.getKorean());
        model.addAttribute("categories", categories);
        model.addAttribute("largeCategories", LargeCategory.values());
        return "categoryList";
    }
    }


