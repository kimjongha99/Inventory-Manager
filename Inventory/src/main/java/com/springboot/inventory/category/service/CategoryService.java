package com.springboot.inventory.category.service;

import com.springboot.inventory.common.dto.ResponseDTO;
import com.springboot.inventory.common.entity.Category;

import java.util.List;

public interface CategoryService {

    ResponseDTO<List<Category>> getCategoryList();

}
