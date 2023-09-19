package com.springboot.inventory.user.dto;

import lombok.Getter;

@Getter
public class AdminLoginRequestDto {
    private String username;
    private String password;
}