package com.springboot.inventory.supply.dto;

import com.springboot.inventory.common.entity.Category;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.RequestTypeEnum;
import lombok.Data;

import javax.persistence.*;

@Data
public class SupplyRegisterDTO {

    private Category category;

    private Integer amount;

    private String modelName;

    @Enumerated(value = EnumType.STRING)
    private RequestTypeEnum state;

    private User user;

}
