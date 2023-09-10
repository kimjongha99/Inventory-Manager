package com.springboot.inventory.user.dto;

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
    private Integer role;

    private Boolean alarm;

    private Boolean deleted;
}
