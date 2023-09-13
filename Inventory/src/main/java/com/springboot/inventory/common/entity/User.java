package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.Builder;
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

    // 아이디
    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    // 유저 정보
    private String username;
    private String tel;


//     토큰
    private String token;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = true)
    private Boolean alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(nullable = true)
    private Boolean deleted;

    @Builder
    public User(String email, String token, String password, String username, String tel,
                UserRoleEnum role, Team team, Boolean deleted) {
        this.email = email;
        this.token = token;
        this.password = password;
        this.username = username;
        this.tel = tel;
        this.role = role;
        this.team = team;
        this.deleted = deleted;
    }

    public void update(String username, Team team, String tel, String password) {
        this.username = username;
        this.team = team;
        this.tel = tel;
        this.password = password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void refreshToken(String token) {
        this.token = token;
    }

    public void changeRole(UserRoleEnum role) {
        // 부여하는 권한을 가지고 있는 경우, 권한을 취소할 수 있음
        this.role = this.role == role ? UserRoleEnum.USER : role;
    }
}
