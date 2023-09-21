package com.springboot.inventory.category.repository;

import com.springboot.inventory.category.dto.CategoryDto;
import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByLargeCategory(LargeCategory largeCategory);

    List<Category> findByLargeCategory(LargeCategory largeCategory);

    Category findByLargeCategoryAndCategoryName(LargeCategory largeCategory, String categoryName);
}
