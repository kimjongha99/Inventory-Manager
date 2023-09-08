package com.springboot.common.entity;

import com.springboot.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false , unique = true)
    private String email; // 이메일 = 아이디

    @Column(nullable = false)
    private String password; //패스워드

    @Column(nullable = false)
    private String name;
    private String tel;

    private boolean alarm;

    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    private UserRoleEnum role;

    @Builder
    public User(String email, String password, String name, String tel, boolean alarm, Boolean deleted, Group group) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.alarm = alarm;
        this.deleted = deleted;
        this.group = group;
    }

}
