package com.springboot.inventory.common.initdata;

import com.springboot.inventory.category.repository.CategoryRepository;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInit(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        initializeCategories();
    }

    private void initializeCategories() {
        for (LargeCategory largeCategory : LargeCategory.values()) {
            // 이미 존재하는 카테고리인지 확인
            if (!categoryRepository.existsByLargeCategory(largeCategory)) {
                Category category = new Category();
                category.setLargeCategory(largeCategory);
                category.setCategoryName(largeCategory.getKorean());
                categoryRepository.save(category);
            }
        }
    }
}
