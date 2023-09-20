package com.springboot.inventory.category.service;

import com.springboot.inventory.category.dto.categoryServiceDto;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public categoryServiceDto saveCategory(String categoryName, LargeCategory largeCategory) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setLargeCategory(largeCategory);
        category.setDeleted(false);
        Category savedCategory = categoryRepository.save(category);

        return new categoryServiceDto(savedCategory.getCategoryId(), savedCategory.getCategoryName(), savedCategory.getLargeCategory());
    }

    public List<categoryServiceDto> getCategoriesByLargeCategory(LargeCategory largeCategory) {
        List<Category> categories = categoryRepository.findByLargeCategoryAndDeleted(largeCategory, false);
        return categories.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private categoryServiceDto toDTO(Category category) {
        return categoryServiceDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .largeCategory(category.getLargeCategory())
                .build();
    }
}
