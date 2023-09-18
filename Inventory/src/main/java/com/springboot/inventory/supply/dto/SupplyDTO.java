package com.springboot.inventory.supply.dto;

import com.springboot.inventory.common.enums.LargeCategory;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SupplyDTO {

    private String serialNum;
    private String modelContent;
    private int amount;
    private String image;
    private String imagePath;
    private String modelName;
    private SupplyStatusEnum status;
   /* private Long userId; // 사용자 ID*/
    private LargeCategory largeCategory;
    private String categoryName;
    private MultipartFile multipartFile;
}
