package com.springboot.inventory.user.dto;


import com.springboot.inventory.common.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {
    private Long userId;
    private String name;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUser_id())
                .name(user.getName())
                .build();
    }
}
