package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MasterResponseDto {
    private Boolean checkGroup;
    private UserRoleEnum userRole;

    public static MasterResponseDto of(Boolean checkGroup) {
        return MasterResponseDto.builder()
                .checkGroup(checkGroup)
                .userRole(UserRoleEnum.MASTER)
                .build();
    }
}
