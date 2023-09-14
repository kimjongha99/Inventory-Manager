package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.Group;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserAllDto {
    private Long user_id;
    private String username;
    private String password;
    private String name;
    private String tel;
    private UserRoleEnum role;
    private Boolean alarm;
    private Group group;
    private Boolean deleted;
    private LocalDateTime created_at;

    @Builder
    public UserAllDto(Long user_id, String username, String password, String name,String tel, UserRoleEnum role, Boolean alarm ,Group group, Boolean deleted, LocalDateTime created_at )
    {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.role = role;
        this.alarm = alarm;
        this.group = group;
        this.deleted = deleted;
        this.created_at = created_at;
    }

    public static UserAllDto toDto(User user){ // User Entity를 받아 UserDto로 변환
        return UserAllDto.builder()
                .user_id(user.getUser_id())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .tel(user.getTel())
                .role(user.getRole())
                .alarm(user.getAlarm())
                .group(user.getGroup())
                .deleted(user.getDeleted())
                .created_at(user.getCreated_at())
                .build();
    }

}
