package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRoleEnum;
import com.springboot.inventory.user.dto.UserDto;
import com.springboot.inventory.user.dto.UserUpdateDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="user") // User Entity 이름
@Getter
@NoArgsConstructor  // 기본 생성자를 자동으로 생성
@EntityListeners(AuditingEntityListener.class)
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

    @Getter
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role; // 권한

    private Boolean alarm; // 알림

    @ManyToOne(fetch = FetchType.LAZY) // 호출될 시에만 작동
    @JoinColumn(name = "group_id") // group_id를 조인함
    private Group group; // 그룹번호

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false; // 삭제여부

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime created_at;

     // 선택적으로 생성자를 사용하기 위해 user_id만 제거
     @Builder
     public User(String username, String password, String name, String tel, UserRoleEnum role, Boolean alarm, Group group, Boolean deleted) {
         this.username = username;
         this.password = password;
         this.name = name;
         this.tel = tel;
         this.role = role;
         this.alarm = alarm;
         this.group = group;
         this.deleted = deleted;
     }

    public static User toEntity(UserDto userDto){ // UserDto를 받아 User로 변환
         return User.builder()
                 .username(userDto.getUsername())
                 .password(userDto.getPassword())
                 .name(userDto.getName())
                 .tel(userDto.getTel())
                 .role(userDto.getRole())
                 .alarm(userDto.getAlarm())
                 .group(userDto.getGroup())
                 .deleted(userDto.getDeleted())
                 .build();
     }

    public void update(UserUpdateDto userUpdateDto) { // 이름, 전화번호, 연락처, 알람 수신 여부, 그룹만 업데이트
        this.name = userUpdateDto.getName();
        this.tel = userUpdateDto.getTel();
        this.alarm = userUpdateDto.getAlarm();
        this.group = userUpdateDto.getGroup();
    }
}
