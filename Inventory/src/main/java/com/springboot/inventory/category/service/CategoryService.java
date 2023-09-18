package com.springboot.inventory.category.service;

import com.springboot.inventory.category.dto.CategoryDTO;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .categoryName(categoryDTO.getCategoryName())
                .largeCategory(categoryDTO.getLargeCategory())
                .deleted(false)
                .build();

        category = categoryRepository.save(category);
        return categoryToDTO(category);
    }

    private CategoryDTO categoryToDTO(Category category) {
        return CategoryDTO.builder()
                .categoryName(category.getCategoryName())
                .largeCategory(category.getLargeCategory())
                .deleted(category.getDeleted())
                .build();
    }

}
