package com.springboot.inventory.category.service.impl;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.category.service.CategoryService;
import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ResponseDTO<List<Category>> getCategoryList() {

        List<Category> categoryList = categoryRepository.findAll();

        return new ResponseDTO<>(true, categoryList);
    }

}
