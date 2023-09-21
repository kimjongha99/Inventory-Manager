package com.springboot.inventory.category.dto;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class categoryServiceDto{
    private Long categoryId;
    private String categoryName;
    private LargeCategory largeCategory;
}