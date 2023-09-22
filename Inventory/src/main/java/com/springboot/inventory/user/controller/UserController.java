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
        return "users/signUpPage";
    }


    @GetMapping("/logout")
    public String logOug() { return "/index"; }

    @GetMapping("/ManagerPage")
    public String ManagerPage() { return "users/ManagerPage"; }
    @GetMapping("/MyPage")
    public String MyPage() {return "users/MyPage"; }

    @GetMapping("/checkPassword")
    public String checkPasswordPage() { return "users/CheckPassword"; }

}