package com.springboot.inventory.user.dto;


import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {
    private String name;
    private String groupName;
    private String tel;
    private Boolean alarm;

//    public static UserInfoResponseDto of(User user) {
//        return UserInfoResponseDto.builder()
//                .name(user.getName())
//                .groupName(user.getRole() == UserRoleEnum.MASTER ? Company.BIPUMIN.getCompanyName() : user.getDepartment().getDeptName())
//                .tel(user.getTel())
//                .alarm(user.getAlarm())
//                .build();
//    }
}
