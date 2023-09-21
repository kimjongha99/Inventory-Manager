package com.springboot.inventory.category.dto;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private LargeCategory largeCategory;

    private String categoryName;

    @Builder.Default
    private Boolean deleted = false;
}
