package com.springboot.inventory.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private  String email;

    private String password;


    private String name;

    private String tel;


    private Integer role;

    private Boolean alarm;

    private Boolean deleted;
}
