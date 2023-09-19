package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto {
    private String email;
    private String name;
    private UserRoleEnum roles;
    private String tel;

    public static UserInfoDto toDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setName(user.getUsername());
        userInfoDto.setRoles(user.getRoles());
        userInfoDto.setTel(user.getTel());
        return userInfoDto;
    }

}
