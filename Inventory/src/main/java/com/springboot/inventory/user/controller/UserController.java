package com.springboot.inventory.user.controller;

import com.springboot.inventory.common.entity.User;
import com.springboot.inventory.user.dto.UserDto;
import com.springboot.inventory.user.security.UserDetailsImpl;
import com.springboot.inventory.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    //회원가입 페이지 이동
    @GetMapping("user/signup")
    public String signupForm(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            User user = userDetails.getUser();
            model.addAttribute("user", user);
        }catch (NullPointerException e){
            return "signup";
        }
        return "signup";
    }
    //회원가입
    @ResponseBody
    @PostMapping("user/signup")
    public User signUp(@RequestBody UserDto userDto) {
        User user = userService.signup(userDto);
        System.out.println(user);
        return user;
    }
    @ResponseBody
    @GetMapping("user/nicknamecheck")
    public User nicknameCheck(@RequestBody String nickname_give) {
        System.out.println(nickname_give);
        return userService.nicknameCheck(nickname_give);
    }
    //로그인 페이지 이동
    @GetMapping("user/login")
    public String loginForm(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            User user = userDetails.getUser();
            model.addAttribute("user", user);
        }catch (NullPointerException e){
            model.addAttribute("user", "");
            return "login";
        }
        return "login";
    }
    @PostMapping("user/check_dup")
    @ResponseBody
    public String nicknameDuplicate(@ModelAttribute("username_give") String username) {
        return userService.findByNickname(username);
    }
}
