package com.springboot.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    // 아이디
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // 이름
    @Column(nullable = false)
    private String name;

    private String tel;

//  private String image;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private boolean alarm;

    @Column(nullable = false)
    private Boolean delete;



}