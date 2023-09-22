package com.springboot.inventory.request.dto;

import com.springboot.inventory.common.entity.User;
import lombok.Data;

@Data
public class RentalRequestDTO {

    private String type;
    private String category;
    private String content;
    private User user;

}
