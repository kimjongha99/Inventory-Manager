package com.springboot.inventory.supply.dto;


import com.springboot.inventory.common.entity.User;
import lombok.Data;


@Data
public class SupplyRegisterDTO {

    private String category;

    private Integer amount;

    private String modelName;

    private User user;

}
