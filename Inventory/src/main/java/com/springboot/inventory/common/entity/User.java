package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRoleEnum;
import lombok.*;

import javax.persistence.*;

@Entity(name="user") // User Entity 이름
@Getter
@NoArgsConstructor
public class User extends Timestamped { // TimeStamped를 상속

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql AUTO_INCREMENT
    private Long user_id; // 유저번호

    @Column(nullable = false, unique = true) // NOT NULL, 유니크
    private String username; // 이메일
    private String password; // 패스워드

    @Column(nullable = false)
    private String name; //이름

    private String tel; // 전화번호

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role; // 권한

    private Boolean alarm; // 알림

    @ManyToOne(fetch = FetchType.LAZY) // 호출될 시에만 작동
    @JoinColumn(name = "group_id") // group_id를 조인함
    private Group group; // 그룹번호

    @Column(nullable = false)
    private Boolean deleted; // 삭제여부

    @Builder // 선택적으로 생성자를 사용하기 위해 user_id만 제거
    public User(String username, String password, String tel, UserRoleEnum role, Boolean alarm ,Group group, Boolean deleted ){
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.role = role;
        this.alarm = alarm;
        this.group = group;
        this.deleted = deleted;
    }


}
