package com.springboot.common.entity;

import com.springboot.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    // 아이디
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    // 이름
    @Column(nullable = false)
    private String name;

    private String tel;

//    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private boolean alarm;

    @Column(nullable = false)
    private Boolean delete;

    @Builder
    public User(String email, String password, String name, String tel,
                UserRoleEnum role, Boolean alarm, Group group, Boolean deleted) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.role = role;
        this.alarm = alarm;
        this.group = group;
        this.delete = delete;
    }


    public void update(String name, Group group, String tel, Boolean alarm, String password) {
        this.name = name;
        this.group = group;
        this.tel = tel;
        this.alarm = alarm;
        this.password = password;
    }


}