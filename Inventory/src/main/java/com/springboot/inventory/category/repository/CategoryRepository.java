package com.springboot.inventory.category.repository;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.enums.LargeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByLargeCategory(LargeCategory largeCategory);


    // LargeCategory와 삭제되지 않은 Category 목록을 검색하는 메서드
    List<Category> findByLargeCategoryAndDeleted(LargeCategory largeCategory, Boolean deleted);
}
