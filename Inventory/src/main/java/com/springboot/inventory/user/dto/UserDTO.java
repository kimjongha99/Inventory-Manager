package com.springboot.inventory.user.dto;

import com.springboot.inventory.common.entity.Team;
import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.common.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor //기본생성자를 자동으로 만들어준다.
@AllArgsConstructor // 필드를 매개변수로하는 생성자를 만들어준다.
@ToString
public class UserDTO { // DTO: 회원정보에 필요한 내용을 필드로 저장하고 private로 감춘다
    private Long id;
    private String username;
    private String password;
    private String nickname;

    private String tel;
//    private boolean alarm;
//    private boolean deleted;
    private Team team;
//    private UserRole role;


    public static UserDTO toUserDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setNickname(user.getNickname());
        userDTO.setTel(user.getTel());
        userDTO.setTeam(user.getTeam());
        return userDTO;
    }
}
