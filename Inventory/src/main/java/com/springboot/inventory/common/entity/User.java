package com.springboot.inventory.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity(name = "users")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String googleId;

    // 아이디
    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    // 이름
    private String empName;

    private String phone;

    private String image;

    // 구글 액세스 토큰
    @Column(nullable = false)
    private String accessToken;

    // SMS 알림 ON/OFF
    @Column(nullable = false)
    private Boolean alarm

    @Column(nullable = false)
    private Boolean deleted;
}
