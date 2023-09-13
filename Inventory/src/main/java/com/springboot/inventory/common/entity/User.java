package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private  String email;

    private String password;

    @Column(nullable = false, unique = true)
    private String name;

    private String tel;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

//    @Column(nullable = false)
//    private Boolean alarm;

    private Boolean deleted;

}
