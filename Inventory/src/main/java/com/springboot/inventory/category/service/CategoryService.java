package com.springboot.inventory.category.service;

import com.springboot.inventory.category.dto.CategoryDto;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> findByLargeCategory(LargeCategory largeCategory) {
        return categoryRepository.findByLargeCategory(largeCategory);
    }




}
