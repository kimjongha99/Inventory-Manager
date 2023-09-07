package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto{
    private Boolean checkUser;
    private Boolean isAdmin;
    private UserRoleEnum userRole;

    public static LoginResponseDto of(User user, Boolean checkUser) {
        return LoginResponseDto.builder()
                .checkUser(checkUser)
                .isAdmin(user.getRole().equals(UserRoleEnum.ADMIN))
                .userRole(user.getRole())
                .build();
    }
}
