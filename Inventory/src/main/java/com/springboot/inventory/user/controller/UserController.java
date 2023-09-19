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

    @GetMapping("/LogInPage")
    public String LogInPage() {
        return "LogInPage";
    }

    @GetMapping("/signUpPage")
    public String signUpPage() {
        return "signUpPage";
    }

    @GetMapping("/LandingPage")
    public String LandingPage() { return "LandingPage"; }

    @GetMapping("/logOut")
    public String logOug() { return "/index"; }

    @GetMapping("/MangerPage")
    public String ManagerPage() { return "/ManagerPage"; }

    @GetMapping("/MyPage")
    public String MyPage() {return "/MyPage"; }


}