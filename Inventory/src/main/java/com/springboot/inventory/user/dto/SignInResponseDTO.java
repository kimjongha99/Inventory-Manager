package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDTO {

    private Boolean isAdmin;
    private UserRoleEnum role;

    public static SignInResponseDTO of(User user) {
        return SignInResponseDTO.builder()
                .isAdmin(user.getRole().equals(UserRoleEnum.ADMIN))
                .role(user.getRole()).build();
    }
}
