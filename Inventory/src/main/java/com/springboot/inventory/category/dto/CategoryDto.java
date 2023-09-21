package com.springboot.inventory.category.dto;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private LargeCategory largeCategory;

    private String categoryName;

    @Builder.Default
    private Boolean deleted = false;
}
