package com.springboot.inventory.common.entity;

import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "user")
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

    private Integer role;

//    @Column(nullable = false)
//    private Boolean alarm;

    private Boolean deleted;



}
