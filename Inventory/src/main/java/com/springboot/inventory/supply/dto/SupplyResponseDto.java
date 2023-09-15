package com.springboot.inventory.supply.dto;

import com.springboot.inventory.supply.domain.entity.Category;
import com.springboot.inventory.supply.domain.entity.Supply;
import com.springboot.inventory.supply.domain.entity.User;
import com.springboot.inventory.supply.domain.enums.SupplyStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class SupplyResponseDto {

    private Long supplyId;
    private String serialNum;
    private String modelContent;
    private int amount;
    private String modelName;
    private boolean deleted;
    private User user; //사용자
    private Category category; // 분류


}
