package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User extends Timestamped{

    @Id //기본키 설정z
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id; //Long = mysql 에서 bigint

    @Column(nullable = false , unique = true) // 유니크 제약조건 추가 , 낫널
    private String username; // 아이디

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Supply> supplies = new ArrayList<>();

    private Boolean deleted;

    @Builder
    public User(String username, UserRole role, Boolean deleted) {
        this.username = username;
        this.role = role;
        this.deleted = deleted;
    }
}
