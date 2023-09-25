package com.springboot.inventory.supply.dto;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.SupplyStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SupplyDetailsDto {
    private Long supplyId;
    private String serialNum;
    private String modelContent;
    private String image;
    private String imagePath;
    private String modelName;
    private boolean deleted;
    private String username;
    private String status;
    private String categoryName; // 분류
    private LocalDateTime createdAt;

    public static SupplyDetailsDto fromSupplyDetails(Supply supply) {
        SupplyDetailsDto dto = new SupplyDetailsDto();
        dto.setCreatedAt(supply.getCreatedAt());
        dto.setSupplyId(supply.getSupplyId());
        dto.setSerialNum(supply.getSerialNum());
        dto.setModelContent(supply.getModelContent());
        dto.setImage(supply.getImage());
        dto.setImagePath(supply.getImagePath());
        dto.setModelName(supply.getModelName());
        dto.setDeleted(supply.isDeleted());
        dto.setCategoryName(supply.getCategory().getCategoryName());

        if (supply.getUser() != null && supply.getUser().getUsername() != null) {
            dto.setUsername(supply.getUser().getUsername());
        } else {
            dto.setUsername("");  // 또는 다른 기본값 설정
        }

        dto.setStatus(supply.getStatus().toString());
        // 예를 들어, supply 객체에 getCategory()나 getCreatedAt() 같은 메서드가 있다면
        // categoryName와 createdAt을 설정할 수 있습니다.
        // dto.setCategoryName(supply.getCategory().getCategoryName());
        // dto.setCreatedAt(supply.getCreatedAt());

        return dto;
    }
}
