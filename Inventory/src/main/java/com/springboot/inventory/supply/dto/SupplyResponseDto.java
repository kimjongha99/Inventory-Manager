package com.springboot.inventory.Supply.dto;

import com.springboot.inventory.common.entity.Supply;
import com.springboot.inventory.common.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SupplyResponseDto {

    private Long supplyId;
    private String serialNum;
    private int amount;
    private String modelName;
    private String status;
    private String username; //사용자
    private String categoryName; // 분류
    private LocalDateTime createdAt;

    public SupplyResponseDto(Supply supply){
        User user = supply.getUser();

        this.supplyId = supply.getSupplyId();
        this.modelName = supply.getModelName();
        this.serialNum = supply.getSerialNum();
        this.amount = supply.getAmount();
        this.createdAt = supply.getCreatedAt();
        this.status = supply.getStatus().getKorean();
        if (supply.getUser() != null) {
            this.username = supply.getUser().getUsername();
        }
        if (supply.getCategory() != null) {
            this.categoryName = supply.getCategory().getCategoryName();
        }

    }



}
