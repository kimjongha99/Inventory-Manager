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
    private String username;
    private UserRoleEnum roles;
    private String tel;
    private String team;
    private boolean deleted;

    public static UserInfoDto toDto(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setRoles(user.getRoles());
        userInfoDto.setTel(user.getTel());
        userInfoDto.setTeam(user.getTeam());
        return userInfoDto;
    }

}
