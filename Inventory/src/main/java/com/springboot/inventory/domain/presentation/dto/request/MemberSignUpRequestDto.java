package com.springboot.inventory.domain.presentation.dto.request;

import com.springboot.inventory.domain.domain.Member;
import com.springboot.inventory.domain.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberSignUpRequestDto {

    private String email;
    private String nickname;
    private int age;
    private String password;


    @Builder
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .age(age)
                .password(password)
                .role(Role.USER)
                .build();
    }
}