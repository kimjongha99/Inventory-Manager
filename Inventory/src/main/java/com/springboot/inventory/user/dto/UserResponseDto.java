package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long User_id;
    private String username;
    private String name;
    private String phone;
    private String image;
    private UserRoleEnum role;
    private Boolean alarm;  //널 허용 ?

    public UserResponseDto(User user) {
        this.User_id = user.getUser_id();
        this.username = user.getUsername();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.role = user.getRole();
        this.alarm = user.getAlarm();
    }
}
