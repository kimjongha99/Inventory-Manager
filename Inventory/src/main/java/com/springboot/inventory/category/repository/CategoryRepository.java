package com.springboot.inventory.category.repository;

import com.springboot.inventory.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

    Optional<Category> findByCategoryName(String categoryName);

}
