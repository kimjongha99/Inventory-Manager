package com.springboot.inventory.Supply.dto;

import com.springboot.inventory.common.enums.LargeCategory;
import lombok.Data;

@Data
public class SupplyRequestDto {

    private LargeCategory largeCategory;

    private String categoryName;

    private String modelName;

    private String serialNum;
    private Long partnersId;
   // private UseType useType;
    private Long userId;
    private Long deptId;
    private String image;
   // private MultipartFile multipartFile;
}
