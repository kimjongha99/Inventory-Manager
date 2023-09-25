package com.springboot.inventory.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    } // 기본 페이지

    @GetMapping("/index")
    public String mainPage() { return "index"; } // 인덱스 페이지 GET

    @GetMapping("/LoginPage")
    public String LogInPage() {
        return "users/LoginPage";
    } // 로그인 페이지

    @GetMapping("/signUpPage")
    public String signUpPage() {
        return "users/signUpPage";
    } // 회원가입 페이지
    @GetMapping("/logout")
    public String logOug() { return "index"; } // 로그아웃

    @GetMapping("/ManagerPage")
    public String ManagerPage() { return "users/ManagerPage"; } // 관리자 페이지

    @GetMapping("/AdminPage")
    public String AdminPage(){ return "users/AdminPage";} // 어드민 권한 페이지
    @GetMapping("/MyPage")
    public String MyPage() {return "users/MyPage"; } // 마이페이지

    @GetMapping("/checkPassword")
    public String checkPasswordPage() { return "users/CheckPassword"; } // 비밀번호 확인

}