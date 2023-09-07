package com.springboot.inventory.user.dto;


import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MasterLoginResponseDto {
    private Boolean checkDept;
    private UserRoleEnum userRole;

    public static MasterLoginResponseDto of(Boolean checkDept) {
        return MasterLoginResponseDto.builder()
                .checkDept(checkDept)
                .userRole(UserRoleEnum.MASTER)
                .build();
    }
}
