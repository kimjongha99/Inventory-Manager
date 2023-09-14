package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.Group;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private String username;
    private String password;
    private String name;
    private String tel;
    private UserRoleEnum role;
    private Boolean alarm;
    private Group group;
    private Boolean deleted;

    @Builder
    public UserDto(String username, String password, String name,String tel, UserRoleEnum role, Boolean alarm ,Group group, Boolean deleted )
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.role = role;
        this.alarm = alarm;
        this.group = group;
        this.deleted = deleted;
    }
    public static UserDto toDto(User user){ // User Entity를 받아 UserDto로 변환
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .tel(user.getTel())
                .role(user.getRole())
                .alarm(user.getAlarm())
                .group(user.getGroup())
                .deleted(user.getDeleted())
                .build();
    }

}
