package com.springboot.inventory.domain.service.Impl;

import com.springboot.inventory.domain.domain.Member;
import com.springboot.inventory.domain.domain.repository.MemberRepository;
import com.springboot.inventory.domain.presentation.dto.request.MemberSignInRequestDto;
import com.springboot.inventory.domain.presentation.dto.request.MemberSignUpRequestDto;
import com.springboot.inventory.domain.service.MemberService;
import com.springboot.inventory.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Long join(MemberSignUpRequestDto requestDto) {
        if(memberRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = memberRepository.save(requestDto.toEntity());
        member.passwordEncode(passwordEncoder);
        member.addUserAuthority();
        return member.getId();
    }

    @Transactional
    @Override
    public String login(MemberSignInRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입된 이메일이 아닙니다."));
        if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String role = member.getRole().name();
        return jwtTokenProvider.createToken(member.getUsername(), role);
    }
}