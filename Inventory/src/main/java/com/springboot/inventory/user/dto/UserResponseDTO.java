package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.User;
import lombok.*;

@Getter
@Builder
public class UserResponseDTO {

    private  String email;

    private String name;

    public static UserResponseDTO of (User user) {
        return UserResponseDTO.builder().email(user.getEmail()).name(user.getName()).build();
    }

}
