package com.springboot.inventory.common.entity;

import com.springboot.inventory.common.enums.UserRole;
import com.springboot.inventory.user.dto.UserDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User extends Timestamped{

    @Id //기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id; //Long = mysql 에서 bigint

    @Column(nullable = false , unique = true) // 유니크 제약조건 추가 , 낫널
    private String username; // 아이디

    @Column(nullable = false)
    private String password; //패스워드

    @Column(nullable = false)
    private String nickname; //이름
    private String tel; //전화번호

    private boolean alarm;

    private boolean deleted;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    private Team team;

    private UserRole role;

    public static User toUser(UserDTO userDTO) { //dto를 엔티티로 변환
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setNickname(userDTO.getNickname());
        user.setTel(userDTO.getTel());
        user.setTeam(userDTO.getTeam());
        return user;
    }

    public static User toUpdateUser(UserDTO userDTO) { // id까지 업데이트
        User user = new User();
        user.setId((userDTO.getId()));
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setNickname(userDTO.getNickname());
        user.setTel(userDTO.getTel());
        user.setTeam(userDTO.getTeam());
        return user;
    }


}
