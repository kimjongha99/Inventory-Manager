package com.springboot.inventory.supply.dto;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SupplyDto {
    private Long supplyId;
    private String serialNum;
    private String modelContent;
    private int amount;
    private String modelName;
    private SupplyStatusEnum status;

    //이미지
    private String image;
    private String imagePath;
    private MultipartFile multipartFile;

    //카테고리
    private Category category;
    private LargeCategory largeCategory;
    private String categoryName;
    private String directCategoryName; //직접입력받는 소분류

    //사용자
    private User user;
    private Long userId;
    private String username;
    private UserRoleEnum userRole;

}
