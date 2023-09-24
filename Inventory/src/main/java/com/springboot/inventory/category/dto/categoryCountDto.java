package com.springboot.inventory.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class categoryCountDto {
    private String categoryName;
    private Long count;

    public categoryCountDto(String categoryName, Long count) {
        this.categoryName = categoryName;
        this.count = count;
    }

}
