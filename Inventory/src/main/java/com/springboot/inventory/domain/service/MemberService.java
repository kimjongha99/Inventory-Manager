package com.springboot.inventory.domain.service;

import com.springboot.inventory.domain.presentation.dto.request.MemberSignInRequestDto;
import com.springboot.inventory.domain.presentation.dto.request.MemberSignUpRequestDto;

public interface MemberService {

    Long join(MemberSignUpRequestDto requestDto);

    String login(MemberSignInRequestDto requestDto);
}