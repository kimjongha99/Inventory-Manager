package com.springboot.inventory.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @GetMapping("/index")
    public String mainPage() { return "index"; }

    @GetMapping("/LoginPage")
    public String LogInPage() {
        return "users/LoginPage";
    }

    @GetMapping("/signUpPage")
    public String signUpPage() {
        return "signUpPage";
    }

    @GetMapping("/LandingPage")
    public String LandingPage() { return "LandingPage"; }

    @GetMapping("/logout")
    public String logOug() { return "/index"; }

    @GetMapping("/ManagerPage")
    public String ManagerPage() { return "/ManagerPage"; }
    @GetMapping("/MyPage")
    public String MyPage() {return "/MyPage"; }

    @GetMapping("/checkPassword")
    public String checkPasswordPage() { return "/CheckPassword"; }

}