package com.springboot.inventory.common.service;

import com.springboot.inventory.common.dto.MemberSignUpRequestDto;

import java.util.Map;

public interface MemberService {

    // 회원가입
    public Long signUp(MemberSignUpRequestDto requestDto) throws Exception;

    //로그인
    public String login(Map<String, String> members);
}