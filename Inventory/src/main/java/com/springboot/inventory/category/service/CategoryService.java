package com.springboot.inventory.category.service;

import com.springboot.inventory.category.dto.categoryCountDto;
import com.springboot.inventory.category.dto.categoryServiceDto;
import com.springboot.inventory.category.dto.CategoryDto;
import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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

    // request part category
    public ResponseDTO<List<Category>> getCategoryList() {

        System.out.println("서비스 진입");

        System.out.println("카테고리 서칭");
        List<Category> categoryList = categoryRepository.findAll();

        System.out.println("카테고리 서칭 완료");
        System.out.println(categoryList.get(0));
        System.out.println(categoryList.get(1));
        System.out.println(categoryList.get(2));

        return new ResponseDTO<>(true, categoryList);
    }

    @Transactional
    public void deleteCategoryById(Long categoryId) {
        // 카테고리를 삭제합니다.
        categoryRepository.deleteById(categoryId);
    }
    public List<categoryCountDto> getCategoryCountsByLargeCategory(LargeCategory selectedLargeCategory) {
        List<Object[]> results = categoryRepository.countGroupedByLargeCategoryAndCategoryName(selectedLargeCategory);

        List<categoryCountDto> categoryCounts = new ArrayList<>();
        for (Object[] result : results) {
            String categoryName = (String) result[0];
            Long count = (Long) result[1];
            categoryCounts.add(new categoryCountDto(categoryName, count));
        }

        return categoryCounts;
    }
    public List<Category> findByLargeCategory(LargeCategory largeCategory) {
        return categoryRepository.findByLargeCategory(largeCategory);
    }




}
