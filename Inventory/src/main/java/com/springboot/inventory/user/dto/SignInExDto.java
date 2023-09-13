package com.springboot.inventory.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInExDto {
    private String email;
    private String password;
}
