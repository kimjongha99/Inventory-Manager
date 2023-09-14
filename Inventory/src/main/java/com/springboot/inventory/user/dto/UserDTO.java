package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {

    //
    private  String email;

    private String password;


    //
    private String name;

    private String tel;

    //
    private UserRoleEnum role;

    private Boolean alarm;

    private Boolean deleted;
}
