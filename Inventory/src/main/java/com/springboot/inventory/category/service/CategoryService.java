package com.springboot.inventory.category.service;

import com.springboot.inventory.category.dto.CategoryDTO;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
