package com.springboot.inventory.controller;


import com.springboot.inventory.dto.LoginRequestDto;
import com.springboot.inventory.dto.SignUpRequestDto;
import com.springboot.inventory.security.UserDetailsImpl;
import com.springboot.inventory.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*index 뷰*/
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    /*로그인 뷰*/
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        /*이미 로그인된 사용자일 경우 인덱스 페이지로 강제이동.*/
        if (userDetails != null) {
            log.info(userDetails.getMember().getMemberName() + "님이 로그인 페이지로 이동을 시도함. -> index 페이지로 강제 이동 함.");
            return "redirect:/index";
        }

        return "login";
    }

    /*회원가입 뷰*/
    @GetMapping("/signup")
    public String signUp() {
        return "signUp";
    }

    /*vip 전용 페이지 뷰*/
    @GetMapping("/vip")
    public String vip(HttpServletRequest request) {
        return "role";
    }

    /*관리자 페이지 뷰*/
    @GetMapping("/admin")
    public String system(HttpServletRequest request) {
        return "role";
    }

    /*회원가입 API*/
    @PostMapping("/api/signup")
    public String signUp(SignUpRequestDto requestDto) {
        memberService.signUp(requestDto);
        return "redirect:/login";
    }

    /*로그인 API*/
    @PostMapping("/api/login")
    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        memberService.login(requestDto, response);
        return "redirect:/index";
    }

    /*서버 로그 쿠키 테스트 확인용*/
    @GetMapping("/cookie/test")
    public String test(@CookieValue(value = "Authorization", defaultValue = "", required = false) String test) {
        log.info(test);
        return "index";
    }

    /*403, forbidden -> 인가 실패 시*/
    @GetMapping("/forbidden")
    public String forbidden() {
        log.warn("비정상적인 접근: 403 forbidden");
        /*따로 횟수를 기록한다던지 로직*/
        return "redirect:/index";
    }

    /*로그아웃 API*/
    @GetMapping("/api/logout")
    public String logout(@CookieValue(value = "Authorization", defaultValue = "", required = false) Cookie jwtCookie,
                         HttpServletResponse response) {
        /*jwt 쿠키를 가지고와서 제거한다.*/
        jwtCookie.setValue(null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/login";
    }
}