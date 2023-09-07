package com.springboot.inventory.common.dto;

import com.springboot.inventory.common.entity.Group;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
