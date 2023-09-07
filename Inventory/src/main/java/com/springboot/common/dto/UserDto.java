package com.springboot.common.dto;

import com.springboot.common.entity.Group;
import com.springboot.common.enums.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserDto {
    private Long user_id;
    private String username;
    private String password;
    private String tel;
    private UserRoleEnum role;
    private Boolean alarm;
    private Group group;
    private Boolean deleted;
}
