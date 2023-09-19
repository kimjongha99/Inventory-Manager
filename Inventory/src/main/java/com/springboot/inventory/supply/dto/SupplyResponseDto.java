package com.springboot.inventory.supply.dto;

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

    private String categoryName; // 분류
    private String modelName;
    private String serialNum;
    private int amount;
    private String username; //사용자
    private LocalDateTime createdAt;
    private String status;

}
