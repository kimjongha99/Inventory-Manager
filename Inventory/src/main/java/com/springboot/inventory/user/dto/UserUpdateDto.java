package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.Group;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder

public class UserUpdateDto {
    private String username;
    private String name;
    private String tel;
    private Boolean alarm;
    private Group group;

    public UserUpdateDto(String username, String name, String tel, Boolean alarm, Group group) {
        this.username = username;
        this.name = name;
        this.tel = tel;
        this.alarm = alarm;
        this.group = group;
    }
    public static UserUpdateDto toDto(User user) {
        if (user == null) {
            // user 객체가 null인 경우 기본값을 설정하거나 예외를 throw하세요.
            return null; // 또는 다른 기본값을 반환하세요.
        }

        return UserUpdateDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .tel(user.getTel())
                .alarm(user.getAlarm())
                .group(user.getGroup())
                .build();
    }

}
