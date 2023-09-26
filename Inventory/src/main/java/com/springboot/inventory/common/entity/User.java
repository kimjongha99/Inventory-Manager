package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;


@Entity(name = "users")
@NoArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE users SET deleted = true, tel = null, username = CONCAT('탈퇴한 유저#', " +
        "user_id) WHERE user_id = ?")
public class User extends Timestamped implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // 아이디
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    // 유저 정보
    private String username;
    private String tel;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum roles;

    @Column(nullable = true)
    private Boolean alarm;

    @Column(nullable = true)
    private String team;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Builder
    public User(String email, String password, String username, String tel,
                UserRoleEnum roles, String team, Boolean deleted) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.tel = tel;
        this.roles = roles;
        this.team = team;
        this.deleted = deleted;
    }

    public void updateUser(String username, String tel) {
        this.username = username;
        this.tel = tel;
    }

    public void updateTeam(String team) {
        this.team = team;
    }

    public void changePassword(String password) {
        this.password = password;
    }


    public void changeRole(UserRoleEnum roles) {
        // 부여하는 권한을 가지고 있는 경우, 권한을 취소할 수 있음
        this.roles = this.roles == roles ? UserRoleEnum.USER : roles;
    }

    public boolean isDeleted() { return deleted;
    }
}
