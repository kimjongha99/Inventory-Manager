package com.springboot.inventory.domain.presentation;

import com.springboot.inventory.domain.presentation.dto.request.MemberSignUpRequestDto;
import com.springboot.inventory.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public Long join(@RequestBody MemberSignUpRequestDto requestDto) {
        return memberService.join(requestDto);
    }
}