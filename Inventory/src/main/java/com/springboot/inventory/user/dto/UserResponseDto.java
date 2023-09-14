package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long user_Id;
    private String username;

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .user_Id(user.getUser_id())
                .username(user.getUsername())
                .build();
    }
}
