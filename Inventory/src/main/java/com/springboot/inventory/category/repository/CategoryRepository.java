package com.springboot.inventory.category.repository;

import com.springboot.inventory.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();

}
