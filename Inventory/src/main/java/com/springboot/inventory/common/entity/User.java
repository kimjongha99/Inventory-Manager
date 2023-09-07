package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User  extends TimeStamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토인크리먼트
    private Long User_id;


    @Column(nullable = false, unique = true)
    private String username;  //아이디,이메일


    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String  name; // 유저이름

    @Column(nullable = false)
    private String phone;

    private String image;


    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    // SMS 알림 ON/OFF
    @Column(nullable = false)
    private Boolean alarm;

//    @ManyToOne(fetch = FetchType.LAZY)    다대일 주석
//    @JoinColumn(name = "department_id")
//    private Department department;

    @Column(nullable = false)
    private Boolean deleted;

    @Builder
    public User(String googleId, String username, String password, String empName, String phone,
                String image, String accessToken, UserRoleEnum role, Boolean alarm, Boolean deleted) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.role = role;
        this.alarm = alarm;
        this.deleted = deleted;
    }


    public void update(String name, String phone, Boolean alarm, String image, String password) {
        this.name = name;
        this.phone = phone;
        this.alarm = alarm;
        this.image = image;
        this.password = password;
    }


    public void changePassword(String password) {
        this.password = password;
    }



    // 권한 부여
    public void changeRole(UserRoleEnum role) {
        // 부여하는 권한을 이미 가지고 있는 경우라면, 권한을 취소하는 경우이다.
        this.role = this.role == role ? UserRoleEnum.USER : role;
    }
}
