package com.springboot.inventory.domain.presentation.dto.response;

import com.springboot.inventory.domain.domain.Member;
import lombok.Getter;

@Getter
public class MemberSignUpResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private int age;
    private String password;

    public MemberSignUpResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.age = member.getAge();
        this.password = member.getPassword();
    }
}