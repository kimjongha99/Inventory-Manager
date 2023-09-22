package com.springboot.inventory.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class UpdateUserDto {
    @NotEmpty
    private String username;
    private String tel;
}
